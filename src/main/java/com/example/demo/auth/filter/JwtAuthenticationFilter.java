package com.example.demo.auth.filter;

import com.example.demo.auth.jwt.JwtTokenizer;
import com.example.demo.auth.jwt.dto.LoginDto;
import com.example.demo.member.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtTokenizer jwtTokenizer) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenizer = jwtTokenizer;
    }

    // UsernamePasswordAuthenticationFilter의 메서드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println("JwtAuthenticationFilter 진입");
            System.out.println("request uri : " + request.getRequestURI());

            LoginDto loginDto =objectMapper.readValue(request.getInputStream(), LoginDto.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword()
            );

            System.out.println("email : " + loginDto.getUsername());
            System.out.println("password : " + loginDto.getPassword());

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // AbstractAuthenticationProcessingFilter의 메서드

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        Member member = (Member)authResult.getPrincipal();

        String accessToken = delegateAccessToken(member);

        response.setHeader("Authorization", "Bearer " + accessToken);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    /**
     * utils
     */
    //access token 생성
    private String delegateAccessToken(Member member) {
        // custom claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getName());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject);
        return accessToken;
    }
}
