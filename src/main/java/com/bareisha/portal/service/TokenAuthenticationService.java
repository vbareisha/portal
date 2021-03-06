package com.bareisha.portal.service;

import com.bareisha.portal.core.dto.UserAuthentication;
import com.bareisha.portal.core.jwt.TokenHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class TokenAuthenticationService {

    public static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    private final TokenHandler tokenHandler;

    public TokenAuthenticationService(String secret, long ttlPeriod) {
        tokenHandler = new TokenHandler(secret, ttlPeriod);
    }

    public void addAuthentication(HttpServletResponse response, Authentication authentication) {
        final User user = (User) authentication.getDetails();
        response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user, System.currentTimeMillis()));
    }

    public void addAuthentication(HttpServletRequest request, Authentication authentication) {
        final User user = (User) authentication.getDetails();
        HttpSession session = request.getSession();
        session.setAttribute(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user, System.currentTimeMillis()));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute(AUTH_HEADER_NAME) != null) {
//            String token = request.getHeader(AUTH_HEADER_NAME);
            String token = (String) session.getAttribute(AUTH_HEADER_NAME);
            if (token != null) {

                try {
                    String user = tokenHandler.parseUserFromToken(token);
                    if (user != null) {
                        Set<GrantedAuthority> set = getUserRoleFromClaims();
                        User userNotification = new User(user, "", set);
                        return new UserAuthentication(userNotification);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e.getCause());
                }
            }
        }
        return null;
    }

    /**
     * Получаем необходимые роли из токена
     *
     * @return {@link GrantedAuthority}
     */
    public Set<GrantedAuthority> getUserRoleFromClaims() {
        Set<GrantedAuthority> authority = new HashSet<>();
        authority.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authority;
    }
}
