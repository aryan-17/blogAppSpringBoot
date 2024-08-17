package com.example.practice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "wegljndlwejgns";
    public String generateToken(UserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    public Date extractExpirationDate(String token){
        return extractAllClaims(token).getExpiration();
    }

    private Boolean isTokenExpired(String token){
        return extractExpirationDate(token).before(new Date());
    }

    private String createToken(
            HashMap<String, Object> claims,
            String username
    ) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*5))
                .signWith(getSignInKey())
                .compact();
    }

    public SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean validateToken(String token, String username){
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

}
