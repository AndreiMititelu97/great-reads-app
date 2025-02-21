package org.greatreads.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.accessTokenValidityMS}")
    private int jwtExpirationMs;

    public String createToken(String email, String role, int userId) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .subject(email)
                .claims(Map.of(
                        "userId", userId,
                        "role", role
                ))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public String validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        JwtParser parser = Jwts.parser()
                .verifyWith(key)
                .build();

        try {
            Jwt<?, ?> jwt = parser.parse(token.substring(token.indexOf(" ") + 1));
            return ((DefaultClaims)(jwt.getPayload())).getSubject();
        } catch (Exception ex) {
            throw new UsernameNotFoundException("Invalid token");
        }
    }

    public String extractRole(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        JwtParser parser = Jwts.parser()
                .verifyWith(key)
                .build();

        try {
            Jwt<?, ?> jwt = parser.parse(token.substring(token.indexOf(" ") + 1));
            Claims claims = (Claims) jwt.getPayload();
            return claims.get("role", String.class);
        } catch (Exception ex) {
            throw new UsernameNotFoundException("Invalid token");
        }
    }
}