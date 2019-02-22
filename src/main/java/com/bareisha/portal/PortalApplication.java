package com.bareisha.portal;

import com.bareisha.portal.config.SecurityConfig;
import com.bareisha.portal.config.properties.StorageProperties;
import com.bareisha.portal.config.properties.TokenProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SecurityConfig.class)
@EnableConfigurationProperties({TokenProperties.class, StorageProperties.class})
public class PortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }

}