package com.bareisha.portal.repository;

import com.bareisha.portal.core.dto.User;
import com.bareisha.portal.core.dto.UsersYaml;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    private final String db_users;

    @Autowired
    public UserRepository(Environment environment) {
        db_users = environment.getProperty("db.users");
        if (db_users == null || db_users.isEmpty()) {
            throw new RuntimeException("Can't find users database!");
        }
    }

    @Override
    public List<User> getAllUsers() {
        UsersYaml users;
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        URL urlPath = this.getClass().getClassLoader().getResource(db_users);
        String path;
        if (urlPath == null) {
            path = db_users;
        } else {
            path = urlPath.getPath();
        }
        users = getUsersFromYaml(mapper, path);
        return users == null ? new ArrayList<>() : users.getUsers();
    }

    private UsersYaml getUsersFromYaml(ObjectMapper mapper, String path) {
        UsersYaml users;
        File file = new File(path);
        try {
            users = mapper.readValue(file, UsersYaml.class);
        } catch (Exception e) {
            LOGGER.error("Error getting user {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return users;
    }
}
