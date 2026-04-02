package com.crm.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "123456789ABCDEFGHIJKLMNOPQ@#$%^&*";
    public String generateToken(String userName) {
        return Jwts.builder().setSubject(userName).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+86400000))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
    public String extractUserName(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    // 3. Extract Expiration Date
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 4. Generic Method to Extract Any Claim (Required by methods above)
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if Token is Expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate Token (Used by JwtFilter)
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        // Valid if username matches AND token is not expired
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
