package com.ll.topcastingbe.global.security.oauth2;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ll.topcastingbe.domain.member.entity.RefreshToken;
import com.ll.topcastingbe.domain.member.entity.RefreshToken.RefreshTokenBuilder;
import com.ll.topcastingbe.domain.member.repository.RefreshTokenRepository;
import com.ll.topcastingbe.global.security.JwtProps;
import com.ll.topcastingbe.global.security.SecurityConstants;
import com.ll.topcastingbe.global.security.auth.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProps jwtProps;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${custom.site.front_url}")
    private String frontUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        //HMAC512 방식
        String accessToken = JWT.create()
                                     .withSubject(principalDetails.getUsername())
                                     .withExpiresAt(new Date(
                                             System.currentTimeMillis() + SecurityConstants.ACCESS_EXPIRATION_TIME))
                                     .withClaim("username", principalDetails.getUsername())
                                     .sign(Algorithm.HMAC512(jwtProps.secretKey));
        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + accessToken);

        String refreshToken = JWT.create()
                                      .withSubject(principalDetails.getUsername())
                                      .withExpiresAt(new Date(
                                              System.currentTimeMillis() + SecurityConstants.REFRESH_EXPIRATION_TIME))
                                      .withClaim("username", principalDetails.getUsername())
                                      .sign(Algorithm.HMAC512(jwtProps.refreshKey));

        RefreshToken token = RefreshToken.builder()
                                     .username(principalDetails.getUsername())
                                     .token(refreshToken)
                                     .checkFlag(true)
                                     .build();
        refreshTokenRepository.save(token);

        response.addCookie(createCookie("RefreshToken", refreshToken));
        response.addCookie(createCookie("accessToken", accessToken));

        log.info("CustomSuccessHandler - frontUrl : {}", frontUrl);

        response.sendRedirect(frontUrl);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60);
        //cookie.setSecure(true);
        cookie.setPath("/");
//        cookie.setHttpOnly(true);

        return cookie;
    }
}
