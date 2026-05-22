package com.example.demo.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class HmacJwtExample {
    public static void main(String[] args) {
        String secretKey = "my-secret-key";

        // JWT 생성하기
        String jwt = Jwts.builder()
                .claim("role","ADMIN")
                .setSubject("user123")
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        System.out.println("생성된 jwt : " + jwt);

        // JWT 검증하기, parser() 메서드는 지원중단
        Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt);
        System.out.println("JWT 검증 완료");
    }
}
