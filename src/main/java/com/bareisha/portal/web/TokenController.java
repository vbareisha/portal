package com.bareisha.portal.web;

import com.bareisha.portal.core.dto.ExternalUserDto;
import com.bareisha.portal.core.dto.UserAuthentication;
import com.bareisha.portal.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private final TokenAuthenticationService tokenAuthenticationService;

    public TokenController(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> getToken(@RequestBody ExternalUserDto externalUser, HttpServletResponse response) {
        if (true) {
            User user = new User(externalUser.getUserName(), externalUser.getPassword(), new HashSet<>());
            UserAuthentication authentication = new UserAuthentication(user);
            tokenAuthenticationService.addAuthentication(response, authentication);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
