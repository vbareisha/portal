package com.bareisha.portal.web.controller;

import com.bareisha.portal.core.dto.ExternalUserDto;
import com.bareisha.portal.core.dto.UserAuthentication;
import com.bareisha.portal.service.TokenAuthenticationService;
import com.bareisha.portal.service.api.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {

    private static final Logger log = LoggerFactory.getLogger(TokenController.class);

    private final TokenAuthenticationService tokenAuthenticationService;

    private final IUserService userService;

    @Autowired
    public TokenController(TokenAuthenticationService tokenAuthenticationService, IUserService userService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestBody ExternalUserDto externalUserDto, HttpServletResponse response) {
        if (userService.checkUserByNameAndPassword(externalUserDto.getUserName(), externalUserDto.getPassword())) {
            User user = new User(externalUserDto.getUserName(), externalUserDto.getPassword(), new HashSet<>());
            UserAuthentication authentication = new UserAuthentication(user);
            tokenAuthenticationService.addAuthentication(response, authentication);

            Map<String, String> responseJson = new HashMap<String, String>() {{
                put("status", "ok");
            }};
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        }

        log.error("User not found! user = {}", externalUserDto);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("create")
    public ResponseEntity<?> createUser(@RequestBody ExternalUserDto newUser) {
        log.debug("Start to create user {}", newUser);
        boolean result = userService.createUser(newUser.getUserName(), newUser.getPassword());

        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}