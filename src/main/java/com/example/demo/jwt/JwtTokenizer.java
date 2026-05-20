package com.example.demo.jwt;

import io.jsonwebtoken.io.Encoders;

import java.nio.charset.StandardCharsets;

public class JwtTokenizer {

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
