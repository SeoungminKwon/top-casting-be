package com.ll.topcastingbe.global.security;

import com.ll.topcastingbe.domain.member.repository.MemberRepository;
import com.ll.topcastingbe.domain.member.repository.RefreshTokenRepository;
import com.ll.topcastingbe.global.security.auth.PrincipalOauth2UserService;
import com.ll.topcastingbe.global.security.jwt.JwtAuthenticationFilter;
import com.ll.topcastingbe.global.security.jwt.JwtAuthorizationFilter;
import com.ll.topcastingbe.global.security.oauth2.CustomSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class ApiSecurityConfig {

    private final CorsFilter corsFilter;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtProps jwtProps;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PrincipalOauth2UserService oauth2UserService;
    private final CustomSuccessHandler successHandler;

    @Value("${custom.site.front_url}")
    private String frontUrl;


    @Bean //@Bean - 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Order(1)
    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsCustomizer ->
                              corsCustomizer.configurationSource(new CorsConfigurationSource() {
                                  @Override
                                  public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                      CorsConfiguration configuration = new CorsConfiguration();

                                      configuration.setAllowedOrigins(
                                              Collections.singletonList(frontUrl));
                                      configuration.setAllowedMethods(Collections.singletonList("*"));
                                      configuration.setAllowCredentials(true);
                                      configuration.setAllowedHeaders(Collections.singletonList("*"));
                                      configuration.setMaxAge(3600L);

                                      configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                                      configuration.setExposedHeaders(Collections.singletonList("Authorization"));
                                      return configuration;
                                  }
                              }))
                .csrf(csrf ->
                              csrf.disable()
                )
                .headers(
                        headers ->
                                headers.frameOptions(
                                        frameOptions ->
                                                frameOptions.sameOrigin()
                                )
                )
                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 x
                )
                .formLogin(
                        formLogin ->
                                formLogin.disable()
                )
                .httpBasic(
                        httpBasic -> httpBasic.disable()
                )
                .authorizeHttpRequests(
                        authorize ->
                                authorize.
                                        requestMatchers("/h2-console/**").permitAll()
                                        .requestMatchers("/api/**").permitAll()
                                        .anyRequest().permitAll()
                )
                .oauth2Login(
                        oauth2Login ->
                                oauth2Login.userInfoEndpoint((userInfoEndpointConfig ->
                                                                      userInfoEndpointConfig.userService(
                                                                              oauth2UserService)))
                                        .successHandler(successHandler)
                )
//                .addFilter(corsFilter) // 컨트롤러 - @CrossOrigin(인증 x), 필터에 등록 (인증 o)
                .addFilter(
                        new JwtAuthenticationFilter(authenticationManager(), jwtProps, refreshTokenRepository,
                                memberRepository))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), memberRepository, jwtProps,
                        refreshTokenRepository));

        return http.build();
    }

}
