package com.bareisha.portal.service.api;

public interface IUserService {
    boolean checkUserByNameAndPassword(String name, String password);

    boolean createUser(String userName, String password);
}
