package com.example.demo.security;

import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Component
public final class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String signingKey) {
        this.secretKey = generateSecretKey(signingKey);
    }

    public SecretKey generateSecretKey(String signingKey) {
        Objects.requireNonNull(signingKey, "Signing key cannot be null");

        try {
            byte[] decodedKey = Base64.getDecoder().decode(signingKey);
            return Keys.hmacShaKeyFor(decodedKey);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Signing key must be valid Base64 and contain at least 32 bytes", e);
        }
    }

    public String generateToken(String subject, String audience, Map<String, ?> claims, int expiresIn) {
        Objects.requireNonNull(subject, "Subject cannot be null");
        Objects.requireNonNull(audience, "Audience cannot be null");

        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + expiresIn * 1000);

        log.debug("Generating JWT token for subject: {}, audience: {}, issuedAt: {}, expiresIn: {} seconds",
                subject, audience, issuedAt, expiresIn);
        
        return Jwts.builder().subject(subject).id(UUID.randomUUID().toString()).claims(claims).audience().add(audience)
                .and().issuedAt(issuedAt).notBefore(issuedAt).expiration(expiration).signWith(this.secretKey).compact();
    }

    private Claims getPlayload(String token) {
        Objects.requireNonNull(secretKey, "Secret key cannot be null");
        try {
            if(Objects.isNull(token)) {
                throw new IllegalArgumentException("Authentication token is missing");
            }
            return Jwts.parser()
                    .verifyWith(this.secretKey)
                    .clockSkewSeconds(10)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Authentication token has expired");
        } catch (Exception e) {
            throw new IllegalArgumentException("Authentication token is invalid");
        }
    }

    public String getUsername(String token) {
        return this.getPlayload(token).getSubject();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.getPlayload(token);
        return claimsResolver.apply(claims);
    }

    public String getRole(String token) {
        return this.getPlayload(token).get("role", String.class);
    }

    public Date extractExpirationDate(String token) {
        return this.getClaim(token, claims -> claims.getExpiration());
    }

    public boolean isTokenExpired(String token) {
        return this.extractExpirationDate(token).before(new Date());
    }
    
    public boolean isTokenValid(String token) {
        final String username = this.getUsername(token);
        return (username.equals(this.getUsername(token)) && !isTokenExpired(token));
    }
}
