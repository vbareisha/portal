package com.bareisha.portal.service;

import com.bareisha.portal.core.dto.User;
import com.bareisha.portal.repository.IUserRepository;
import com.bareisha.portal.service.api.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserServiceConf.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {

    private static final String USER_NAME = "admin";
    private static final String PASSWORD = "123";
    private static final String ROOM_NUMBER = "1";

    @Autowired
    private IUserService userService;

    @MockBean
    private IUserRepository userRepository;

    @Test
    public void checkUserByNameAndPassword() {
        when(userRepository.getAllUsers()).thenReturn(new ArrayList<User>() {{
            User user = new User(USER_NAME, PASSWORD, ROOM_NUMBER);
            user.setActive(true);
            add(user);
        }});

        Assert.assertTrue(userService.checkUserByNameAndPassword(USER_NAME, PASSWORD));
    }

    @Test
    public void checkUserByNameAndPasswordAndGetError() {
        when(userRepository.getAllUsers()).thenReturn(new ArrayList<>());

        Assert.assertFalse(userService.checkUserByNameAndPassword(USER_NAME, PASSWORD));
    }

    @Test
    public void createUser() {
        when(userRepository.getAllUsers()).thenReturn(new ArrayList<>());
        Assert.assertTrue(userService.createUser(USER_NAME, PASSWORD, ROOM_NUMBER));
    }

    @Test
    public void createUsernadGetErrorExisting() {
        when(userRepository.getAllUsers()).thenReturn(new ArrayList<User>() {{
            add(new User(USER_NAME, PASSWORD, ROOM_NUMBER));
        }});

        Assert.assertFalse(userService.createUser(USER_NAME, PASSWORD, ROOM_NUMBER));
    }
}