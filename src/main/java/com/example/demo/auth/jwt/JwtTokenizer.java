package com.example.demo.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
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
        // Key를 얻고
        Key key = getKeyFromBase64EncodedSecretKey(base64EncodedSecretKey);

        // AccessToken을 만듬
        String accessToken = Jwts.builder()
                .setClaims(claims) // 이 claims는 custom claims임
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact(); // build가 아니라 compact() 메서드로 닫는다
        return accessToken;
    }

    // JWT refreshToken을 생성하는 메서드
    public String generateRefreshToken(
            String subject,
            Date expiration,
            String base64EncodedSecretKey
    ) {
        // Key를 얻고
        Key key = getKeyFromBase64EncodedSecretKey(base64EncodedSecretKey);

        // RefreshToken을 만듬
        String refreshToken = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();

        return refreshToken;
    }

    // Jwt에 넣었던 custom claims를 검증하고, 추출하는 메서드
    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedSecretKey(base64EncodedSecretKey);

        // key를 넣고, build() 한 다음 검증한다
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);

        return claims;
    }

    // Signature 검증하는 메서드
    public void verifySignature(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedSecretKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    // JWT의 만료 일시를 지정하기 위한 메서드, JWT 생성 시 사용된다
    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
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
