package com.bareisha.portal;

import com.bareisha.portal.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SecurityConfig.class)
public class PortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }

}