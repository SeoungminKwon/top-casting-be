package com.ll.topcastingbe.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.topcastingbe.domain.member.dto.LoginRequestDto;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtProps jwtProps;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProps jwtProps,
                                   RefreshTokenRepository refreshTokenRepository, MemberRepository memberRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtProps = jwtProps;
        this.refreshTokenRepository = refreshTokenRepository;
        this.memberRepository = memberRepository;
        setFilterProcessesUrl("/api/v1/auth/login");
    }

    // login요청시 로그인 시도를 위해서 실행되는 함수 ( 실행 순서 1)
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        ObjectMapper om = new ObjectMapper();
        LoginRequestDto loginRequestDto = null;
        try {
            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 유저네임패스워드 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword());

        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);
        return authentication; // 세션에 저장
    }

    //성공시 실행 - JWT 토큰 생성해서 response에 담아주기
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

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

        Cookie refreshCookie = new Cookie("RefreshToken", refreshToken);
//        refreshCookie.setHttpOnly(true);

        Cookie accessCookie = new Cookie("AccessToken", accessToken);
        response.addCookie(refreshCookie);
        response.addCookie(accessCookie);

        RefreshToken token = RefreshToken.builder()
                                     .token(refreshToken)
                                     .username(principalDetails.getUsername())
                                     .checkFlag(true)
                                     .build();
        refreshTokenRepository.save(token);
    }

}
