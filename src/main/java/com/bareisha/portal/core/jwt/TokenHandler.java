package com.bareisha.portal.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public class TokenHandler {

    private final String secret;
    private final long ttlPeriod;

    public TokenHandler(String secret, long ttlPeriod) {
        this.secret = secret;
        this.ttlPeriod = ttlPeriod;
    }

    public String parseUserFromToken(String token) throws Exception {
        final Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        validateToken(claims);
        return claims.getSubject();
    }

    public String createTokenForUser(User user, long ttl) {
        Claims claims = Jwts.claims();

        claims.put("username", user.getUsername());
        claims.put("ttl", ttl);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Проверям данные токена на валидность
     *
     * @param body полезное тело токена
     * @throws AuthenticationException если что то пошло не так - все наверх
     */
    private void validateToken(Claims body) throws Exception {

        final Long ttl = Long.valueOf(safeExtractKey(body, "ttl"));

        if ((ttl + ttlPeriod) < System.currentTimeMillis()) {
            throw new Exception("Token live time is expired!");
        }
    }

    /**
     * Достаем безопасно значение по key из {@link Claims}
     *
     * @param body полезное тело токена
     * @param key  ключик
     * @return значение
     * @throws AuthenticationException если что то пошло не так - все наверх
     */
    public static String safeExtractKey(Claims body, String key) throws Exception {
        return Optional.ofNullable(body)
                .map(b -> b.get(key))
                .map(String::valueOf)
                .orElseThrow(() -> new Exception("Exception extracting where key = " + key + " from jwt token body"));
    }

}
