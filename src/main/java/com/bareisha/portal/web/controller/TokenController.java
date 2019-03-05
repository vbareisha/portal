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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.bareisha.portal.service.TokenAuthenticationService.AUTH_HEADER_NAME;

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
    public ResponseEntity<?> getToken(@RequestBody ExternalUserDto externalUserDto,
                                      HttpServletResponse response,
                                      HttpServletRequest request) {
        if (userService.checkUserByNameAndPassword(externalUserDto.getUserName(), externalUserDto.getPassword())) {
            User user = new User(externalUserDto.getUserName(), externalUserDto.getPassword(), new HashSet<>());
            UserAuthentication authentication = new UserAuthentication(user);
            //tokenAuthenticationService.addAuthentication(response, authentication);
            tokenAuthenticationService.addAuthentication(request, authentication);

            Map<String, String> responseJson = new HashMap<String, String>() {{
                put("status", "ok");
            }};
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        }

        log.error("User not found! user = {}", externalUserDto);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody ExternalUserDto newUser) {
        log.debug("Start to create user {}", newUser);
        boolean result = userService.createUser(newUser.getUserName(), newUser.getPassword(), newUser.getRoomNumber());

        Map<String, String> responseJson = new HashMap<>();
        if (result) {
            responseJson.put("status", "ok");
        } else {
            responseJson.put("status", "error");
        }
        return result ? new ResponseEntity<>(responseJson, HttpStatus.OK) : new ResponseEntity<>(responseJson, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletRequest request) {
        String key = (String) request.getSession().getAttribute(AUTH_HEADER_NAME);
        if (key != null) {
            log.info("Logout user key: " + key);
            request.getSession().removeAttribute(AUTH_HEADER_NAME);
        } else {
            log.error("User key not found in session!");
        }

        Map<String, String> responseJson = new HashMap<String, String>() {{
            put("status", "ok");
        }};

        return new ResponseEntity<>(responseJson, HttpStatus.OK);
    }
}