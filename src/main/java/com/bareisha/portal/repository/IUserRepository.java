package com.bareisha.portal.repository;

import com.bareisha.portal.core.dto.User;

import java.util.List;

public interface IUserRepository {
    List<User> getAllUsers();
}
