package com.demo.mockapi.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms:3600000}")
    private long expirationMs;

    public String generateToken(String subject) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
            .subject(subject)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(getSigningKey())
            .compact();
    }

    public byte[] getSigningKeyBytes() {
        return java.util.Base64.getDecoder().decode(secret);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(getSigningKeyBytes());
    }

}
