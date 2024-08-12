package com.himedia.rentmon_back.security;


import com.himedia.rentmon_back.security.filter.JWTCheckFilter;
import com.himedia.rentmon_back.security.handler.APILgoinSuccessHandler;
import com.himedia.rentmon_back.security.handler.APILoginFailHandler;
import com.himedia.rentmon_back.security.handler.CustomAccessDeninedHadler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //통신제약
        http.cors(
                httpSecurityCorsConfigurer -> {
                    httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
                }
        );

        //위조 방지
        http.csrf(config -> config.disable());


        //세션에 상태 저장
        http.sessionManagement(
                sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        //로그인 처리 설정
        http.formLogin(config -> {
            config.loginPage("/member/login")
                    .usernameParameter("usernameWithRole")
                    .passwordParameter("password"); //loadUserByUername 자동호출
            config.successHandler(new APILgoinSuccessHandler()); //로그인 성공시 실행할 코드를 갖은 클래스
            config.failureHandler(new APILoginFailHandler()); //로그인 실패시 실행할 코드를 갖은 클래스
        });

        //JWT 엑세스 토큰 체크
        http.addFilterBefore(new JWTCheckFilter(), UsernamePasswordAuthenticationFilter.class);

        //접근시 발생한 예외처리 (옉세스 토큰 오류, 로그인 오류 등등
        http.exceptionHandling(config->{
            config.accessDeniedHandler(new CustomAccessDeninedHadler());
        });


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD" , "GET", "POST", "PUT","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization","Cache-Control","Content-Type"));

        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return  source;
    }

}
