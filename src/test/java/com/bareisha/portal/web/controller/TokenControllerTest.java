package com.bareisha.portal.web.controller;


import com.bareisha.portal.core.dto.ExternalUserDto;
import com.bareisha.portal.service.api.IUserService;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test case:
 * - login without data
 * - login with incorrect data
 * - login with correct data
 * - create existing user
 * - create new user
 * - check on forbidden
 */
public class TokenControllerTest extends AbstractControllerTest {

    private static final String URL_LOGIN = "/token/login";

    private static final String URL_CREATE_USER = "/token/create";

    @MockBean
    private IUserService userService;

    @Test
    public void tryToLoginWithEmptyAndGetErrorNadRequest() throws Exception {
        setup();
        MvcResult mvcResult = this.mockMvc.perform(post(URL_LOGIN).contentType(contentType))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void tryToLoginWithInvalidDataAndGetErrorBadRequest() throws Exception {
        setup();

        ExternalUserDto user = getExternalUserDto("test", "test");

        String data = objectMapper.writeValueAsString(user);
        when(userService.checkUserByNameAndPassword(anyString(), anyString())).thenReturn(false);

        MvcResult mvcResult = this.mockMvc.perform(post(URL_LOGIN).contentType(contentType).content(data))
                .andExpect(status().isBadRequest()).andReturn();
    }

    private ExternalUserDto getExternalUserDto(String userName, String password) {
        ExternalUserDto user = new ExternalUserDto();
        user.setUserName(userName);
        user.setPassword(password);
        return user;
    }

    @Test
    public void loginTest() throws Exception {
        setup();

        ExternalUserDto user = getExternalUserDto("bareisha89@gmail.com", "1234");

        String data = objectMapper.writeValueAsString(user);
        when(userService.checkUserByNameAndPassword(anyString(), anyString())).thenReturn(true);

        MvcResult mvcResult = this.mockMvc.perform(post(URL_LOGIN).contentType(contentType).content(data))
                .andExpect(status().isOk()).andReturn();

    }

    @Test
    public void createExistingUserAndGetErrorBadRequest() throws Exception {
        setup();

        ExternalUserDto user = getExternalUserDto("bareisha89@gmail.com", "1234");
        String data = objectMapper.writeValueAsString(user);

        when(userService.createUser(anyString(), anyString())).thenReturn(false);

        MvcResult mvcResult = this.mockMvc.perform(post(URL_CREATE_USER).contentType(contentType).content(data))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void createUser() throws Exception {
        setup();

        ExternalUserDto user = getExternalUserDto("bareisha89@gmail.com", "1234");
        String data = objectMapper.writeValueAsString(user);

        when(userService.createUser(user.getUserName(), user.getPassword())).thenReturn(true);

        MvcResult mvcResult = this.mockMvc.perform(post(URL_CREATE_USER).contentType(contentType).content(data))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getNewsAndGetErrorForbidden() throws Exception {
        setup();

        MvcResult mvcResult = this.mockMvc.perform(post("/news"))
                .andExpect(status().isForbidden()).andReturn();
    }
}
