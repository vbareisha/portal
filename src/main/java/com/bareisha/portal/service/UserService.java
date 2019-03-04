package com.bareisha.portal.service;

import com.bareisha.portal.core.dto.User;
import com.bareisha.portal.repository.IUserRepository;
import com.bareisha.portal.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean checkUserByNameAndPassword(String name, String password) {
        List<User> users = userRepository.getAllUsers();

        for (User user : users) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean createUser(String userName, String password, String roomNumber) {
        // check on existing user
        List<User> users = userRepository.getAllUsers();
        for (User user : users) {
            if (user.getName().equals(userName) && user.getPassword().equals(password) && user.getRoomNumber().equals(roomNumber)) {
                return false;
            }
        }
        // not found any match - create user
        userRepository.createUser(userName, password, roomNumber);
        return true;
    }
}
