package com.bareisha.portal.repository;

import com.bareisha.portal.config.properties.StorageProperties;
import com.bareisha.portal.core.dto.User;
import com.bareisha.portal.core.dto.UsersYaml;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository implements IUserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    private final String db_users;

    private ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    @Autowired
    public UserRepository(StorageProperties storageProperties) {
        db_users = storageProperties.getFile();
        if (db_users == null || db_users.isEmpty()) {
            throw new RuntimeException("Can't find users database!");
        }
    }

    @Override
    public List<User> getAllUsers() {
        UsersYaml users;
        String path = getPathYml();
        users = getUsersFromYaml(mapper, path);
        return users == null ? new ArrayList<>() : users.getUsers();
    }

    private String getPathYml() {
        URL urlPath = this.getClass().getClassLoader().getResource(db_users);
        String path;
        if (urlPath == null) {
            path = db_users;
        } else {
            path = urlPath.getPath();
        }
        return path;
    }

    @Override
    public synchronized void createUser(String userName, String password) {
        List<User> users = getAllUsers();
        users.add(new User(userName, password));
        Map<String, Object> data = new HashMap<>();

        data.put("users", users);
        // save to yml
        try {
            String path = getPathYml();
            File file = new File(path);
            FileWriter fw = new FileWriter(file);
            fw.write(mapper.writeValueAsString(data));
            fw.close();
        } catch (Exception e) {
            LOGGER.error("Error during saving user {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
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
