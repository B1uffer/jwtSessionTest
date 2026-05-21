package com.example.demo.jwt;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtTokenizerTest {
    private static JwtTokenizer jwtTokenizer;
    private String secretKey;
    private String base64EncodedSecretKey;

    @BeforeAll
    public void init() {
        jwtTokenizer = new JwtTokenizer();
        secretKey = "kevin1234123412341234123412341234";

        // a2V2aW4xMjM0MTIzNDEyMzQxMjM0MTIzNDEyMzQxMjM0
        base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);
    }

    @Test
    public void encodeBase64SecretKeyTest() {

    }
}
