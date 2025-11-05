package com.project.SlotSwapper.app.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import com.project.SlotSwapper.app.util.SecretKeyUtil;

import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private Key key;
    private final long expirationMs = 120 * 60_000L; 

    @PostConstruct
    void init() {
        String generatedB64 = SecretKeyUtil.generateBase64HS256Key();

        System.out.println("\n[JWT] Generated HS256 Secret Key (Base64):\n" + generatedB64);
        System.out.println("You can save this key for stable tokens if needed.\n");

        byte[] keyBytes = Base64.getDecoder().decode(generatedB64);

        if (keyBytes.length < 32) {
            throw new IllegalStateException(
                "JWT secret must be at least 32 bytes for HS256. Current length: "
                + keyBytes.length + " bytes. Please use a 256-bit key."
            );
        }

        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Long userId, String email) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .addClaims(Map.of("email", email))
                .setIssuedAt(Date.from(now))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
