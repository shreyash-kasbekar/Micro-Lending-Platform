package com.borrow.moneyLendingPlatform.config;

import com.borrow.moneyLendingPlatform.user.entity.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "your-super-secret-key-your-super-secret-key";
    private final long EXPIRATION = 100 * 60 * 60;

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(Users user){
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId",user.getId())
                .claim("role",user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        System.out.println("===================================================");
        System.out.println("================== generated token is :: "+token);
        return token;
    }

    public String extractUsername(String token) {
        String username = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        System.out.println("===================================================");
        System.out.println("================== with token :: "+token);
        System.out.println("================== Extracted username is :: "+username);

        return username;
    }

    public Long extractUserId(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public boolean validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
        System.out.println("===================================================");
        System.out.println("================== token validated "+token);
        return true;
    }
}
