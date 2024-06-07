package com.example.parking.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * A utility class for generating and validating JWT (JSON Web Token)
 */
@Component
public class JWTGenerator {
    private static final long JWT_EXPIRATION = 1_000_000_000;
    private static final String JWT_SECRET = "secret";

    /**
     * Generates JWT based on authentication data
     * @param authentication authentication object
     * @return generated JWT
     */
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentdate = new Date();
        Date expireDate = new Date(currentdate.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentdate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    /**
     * Extracts user phone from JWT
     * @param token JWT token
     * @return user phone
     */
    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Checks whether the JWT token is valid
     * @param token JWT token
     * @return true, if token is valid, otherwise false
     * @throws AuthenticationCredentialsNotFoundException if token is invalid or expired
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT token was expired or incorrect");
        }
    }
}