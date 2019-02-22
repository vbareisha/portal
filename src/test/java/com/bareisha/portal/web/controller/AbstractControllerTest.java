package com.bareisha.portal.web.controller;

import com.bareisha.portal.PortalApplication;
import com.bareisha.portal.config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.nio.charset.Charset;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {PortalApplication.class, SecurityConfig.class}
)
@DirtiesContext
@TestPropertySource({
        "classpath:application.yml"
})
@RunWith(SpringRunner.class)
@Profile("test")
public abstract class AbstractControllerTest {

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    protected ObjectMapper objectMapper = new ObjectMapper();

    @Resource(name = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME)
    private Filter securityFilter;
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    protected void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilters(securityFilter).build();
    }
}
