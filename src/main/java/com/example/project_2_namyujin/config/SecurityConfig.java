package com.example.project_2_namyujin.config;

import com.example.project_2_namyujin.security.JwtTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    // HttpSecurity: 일종의 Builder처럼 인증 권한 관련 설정 적용 가능
    //      -> (Http 요청에 대한) 보안 설정을 위한 SecurityFilterChain을 구성하는 클래스
    // SecurityFilterChain: CorsFilter, CsrfFilter, UsernamePasswordFilter, AuthorizationFilter, ...
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                    auth -> auth
                            .requestMatchers(
                                    "/users/register",
                                    "/users/login",
                                    "/feeds/user/**"
                            // 특정 유저 전체 피드 조회 /feeds/user/{userId}
                            ).anonymous()
                            .anyRequest().authenticated()
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS
                                )
                )
                .addFilterBefore(
                        jwtTokenFilter,
                        AuthorizationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
