package com.example.demo.security;

import com.example.demo.auth.jwt.JwtTokenizer;
import com.example.demo.auth.filter.JwtAuthenticationFilter;
import com.example.demo.auth.filter.JwtVerificationFilter;
import com.example.demo.auth.handler.MemberAuthenticationFailureHandler;
import com.example.demo.auth.handler.MemberAuthenticationSuccessHandler;
import com.example.demo.utils.CustomAuthorityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfiguration {
    // v2
    private final JwtTokenizer jwtTokenizer;

    // v4
    private final CustomAuthorityUtils customAuthorityUtils;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer, CustomAuthorityUtils customAuthorityUtils) {
        this.jwtTokenizer = jwtTokenizer;
        this.customAuthorityUtils = customAuthorityUtils;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                .headers(header -> header.frameOptions(
                        frameOptions -> frameOptions.sameOrigin())
                )
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // v4
                .formLogin(login -> login.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(HttpMethod.POST, "/*/members").permitAll()
                                .requestMatchers(HttpMethod.GET, "/*/members").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/*/members/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PATCH, "/*/members/**").hasRole("USER")
                                .requestMatchers(HttpMethod.DELETE, "/*/members/**").hasRole("USER")
                                .anyRequest().permitAll()
                )
                .with(new CustomFilterConfigurer(), Customizer.withDefaults());
        return http.build();
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) {
            // authentication 가져옴
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            // jwtAuthenticationFilter에 authentication, jwtToken을 집어넣음
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("/v2/auth/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            // v4
            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, customAuthorityUtils);

            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }

    // v1
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .headers(headers -> headers
//                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
//                )
//                .csrf(csrf -> csrf.disable())
//                .cors(Customizer.withDefaults())
//                .formLogin(form -> form.disable())
//                .httpBasic(basic -> basic.disable())
//                .authorizeHttpRequests(
//                        authorize -> authorize.anyRequest().permitAll()
//                )
//        ;
//        return http.build();
//    }
//
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        // 콘텐츠 표시 오류가 발생했을 땐 /** 를 \\/** 등으로 표기한 뒤 테스트? 해보기
        // 실제 코드 구현시에는 /** 가 맞다고 한다

        return source;
    }
}
