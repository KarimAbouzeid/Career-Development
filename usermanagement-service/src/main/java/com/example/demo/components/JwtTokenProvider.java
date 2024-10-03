package com.example.demo.components;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider   {

    private String secretkey = "a_very_secure_random_string_of_at_least_32_bytes!";
    private long jwtExpirationDate  = 36000000;

    public String generateToken(Authentication authentication){

        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(key())
                .compact();
        return token;
    }

    private Key key(){ // used to sign and verify JWT tokens
        return Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8));

    }

    // extract username from JWT token
    public String getEmail(String token){

        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token){

        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parse(token);
            return true; // Token is valid
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }



    }



}
