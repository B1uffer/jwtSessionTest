package com.example.demo.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenizer {
    @Getter
    @Value("${jwt.key}")
    private String secretKey;

    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    // secretKey를 base64로 인코딩하는 메서드
    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // JWT accessToken을 생성하는 메서드
    public String generateAccessToken(
            Map<String, Object> claims,
            String subject,
            Date expiration,
            String base64EncodedSecretKey
    ) {
        Key key = getKeyFromBase64EncodedSecretKey(base64EncodedSecretKey);
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact(); // build가 아니라 compact() 메서드로 닫는다
        return token;
    }

    public String generateRefreshToken(
            String subject,
            Date expiration,
            String base64EncodedSecretKey
    ) {
        return null;
    }

    /**
     * Utils
     */
    private Key getKeyFromBase64EncodedSecretKey(String base64EncodedSecretKey) {
        // Decoders는 jsonWebToken.io
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);

        // 타입 Key는 Security.key, Keys는 jsonWebToken.security.keys
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }
}
