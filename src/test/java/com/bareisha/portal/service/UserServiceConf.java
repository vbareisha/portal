package com.bareisha.portal.service;

import com.bareisha.portal.repository.IUserRepository;
import com.bareisha.portal.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("com.bareisha.portal.repository")
public class UserServiceConf {

    @Autowired
    private IUserRepository userRepository;

    @Bean
    public IUserService userService() {
        return new UserService(userRepository);
    }
}
