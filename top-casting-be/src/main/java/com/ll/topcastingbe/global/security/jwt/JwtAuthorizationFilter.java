package com.ll.topcastingbe.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.member.entity.RefreshToken;
import com.ll.topcastingbe.domain.member.repository.MemberRepository;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private MemberRepository memberRepository;
    private JwtProps jwtProps;
    private RefreshTokenRepository refreshTokenRepository;

    @Value("${custom.site.front_url}")
    private String frontUrl;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository,
                                  JwtProps jwtProps, RefreshTokenRepository refreshTokenRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
        this.jwtProps = jwtProps;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    //인증이나 권한이 필요한 주소 요청이 있을 때, 해당 필터를 타게 됨
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(SecurityConstants.TOKEN_HEADER);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX) || header == "Bearer undifined") {
            chain.doFilter(request, response);
            return;
        }
        String accessToken = request.getHeader(SecurityConstants.TOKEN_HEADER)
                                     .replace(SecurityConstants.TOKEN_PREFIX, "");
        String refreshToken = "";
        String username = "";
        //토큰 검증 - SecurityContext에 직접 접근해서 세션 만들시 UserDetailsService에 있는 loadByUsername이 호출됨

        try {
            username = JWT.require(Algorithm.HMAC512(jwtProps.secretKey)).build()
                               .verify(accessToken).getClaim("username").asString();

        } catch (JWTVerificationException e) {
            // JWTVerificationException은 JWT 검증 실패(만료, 유효하지 않은 서명 등) 시 발생
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if ("RefreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break; // refreshToken 쿠키를 찾았으므로 반복 종료
                }
            }

            //refreshToken 검사
            RefreshToken findRefreshToken = refreshTokenRepository.findByTokenAndCheckFlag(refreshToken, true);

            if (findRefreshToken == null) {
                chain.doFilter(request, response);
                return;
            }

            try {
                username = JWT.require(Algorithm.HMAC512(jwtProps.refreshKey)).build()
                                   .verify(refreshToken).getClaim("username").asString();
            } catch (JWTVerificationException ee) {
                //검증 실패시 uncheck
                findRefreshToken.uncheck();
                response.sendRedirect(frontUrl+"/login");
                chain.doFilter(request, response);
                return;
            }

            String newAccessToken = JWT.create()
                                            .withSubject(username)
                                            .withExpiresAt(new Date(
                                                    System.currentTimeMillis()
                                                            + SecurityConstants.ACCESS_EXPIRATION_TIME))
                                            .withClaim("username", username)
                                            .sign(Algorithm.HMAC512(jwtProps.secretKey));

            response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + newAccessToken);

        }

        if (username != null) {
            Member findMember = memberRepository.findByUsername(username);

            //권한 처리
            PrincipalDetails principalDetails = new PrincipalDetails(findMember);

            //인증처리를 하는 것이 아니기 때문에 password는 null처리 - username이 null이 아니면 이미 사용자가 인증된 것
            //JWT 토큰 서명이 정상이면 Authentication객체를 만들어준다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
                    principalDetails.getAuthorities());

            //권한 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
