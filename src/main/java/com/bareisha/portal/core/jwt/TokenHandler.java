package com.bareisha.portal.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.User;

public class TokenHandler {

    private final String secret;

    public TokenHandler(String secret) {
        this.secret = secret;
    }

    public String parseUserFromToken(String token) {
        final Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String createTokenForUser(User user) {
        Claims claims = Jwts.claims();

        claims.put("username", user.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
