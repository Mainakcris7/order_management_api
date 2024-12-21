package com.practice.order_management.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
@Scope("singleton")
public class JwtUtils {
    private String SECRET_KEY = "U3BzY2FtZWF0aWNhbDItZ29vZHMwIHBhcnR5eSBuZWVkIFNvY2lhbCBhcyBFbmQgb2YgQWN0aW9uIGNvbWJpbmF0aW9u";

    public SecretKey getSecureKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String generateJWTToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        // claims.put("role", "admin"); // We can add role like this, if needed
        System.out.println("Secure key: " + getSecureKey());
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                // 7 days validity after the creation of the token
                .expiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .and()
                .signWith(getSecureKey())
                .compact();
    }

    // Extracting user name from token
    public String extractUserName(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    // Validate token
    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        final String userName = extractUserName(jwtToken);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    // Check if the token has expired
    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    // Extracting expiration date from token
    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    // Extracting claims from token
    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }

    // Extracting all claims from token
    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getSecureKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }
}
