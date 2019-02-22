package com.bareisha.portal.config;

import com.bareisha.portal.config.properties.TokenProperties;
import com.bareisha.portal.service.TokenAuthenticationService;
import com.bareisha.portal.web.filter.StatelessAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String TOKEN_URL = "/token/login";
    private final String CREATE_USER_URL = "/token/create";
    private final String MAIN_URL = "/main/**";

    private final String USER_ROLE = "USER";

    private final TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public SecurityConfig(TokenProperties tokenProperties) {
        super(true);
        tokenAuthenticationService = new TokenAuthenticationService(tokenProperties.getSecretKey(), tokenProperties.getTtlPeriod());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().and()
                .anonymous().and()
                .servletApi().and()
                .authorizeRequests()
                // Allow anonymous logins
                .antMatchers(TOKEN_URL, CREATE_USER_URL, "/", "/css/**", "/js/**").permitAll()
                .antMatchers(MAIN_URL).hasAnyRole(USER_ROLE)
                .anyRequest().authenticated().and()
                .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return tokenAuthenticationService;
    }
}
