package com.example.demo.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class RsaJwtExample {
    public static void main(String[] args) throws Exception{
        // RSA 키 쌍 생성하기
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();

        String jwt = Jwts.builder()
                .claim("role","ADMIN")
                .setSubject("user123")
                .signWith(SignatureAlgorithm.RS256, keyPair.getPrivate())
                .compact();

        System.out.println("RSA 서명 기반 JWT : " + jwt);

        // JWT 검증하기 (공개키 사용)
        Jwts.parser()
                .setSigningKey(keyPair.getPublic())
                .parseClaimsJws(jwt);

        System.out.println("JWT 검증 완료");
    }
}
