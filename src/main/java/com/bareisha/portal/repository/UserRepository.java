package com.bareisha.portal.repository;

import com.bareisha.portal.core.dto.User;
import com.bareisha.portal.core.dto.UsersYaml;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepository implements IUserRepository {

    @Override
    public List<User> getAllUsers() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("users.yml")).getPath();

        UsersYaml users = null;
        File file = new File(path);
        if (file.exists()) {
            try {
                users = mapper.readValue(file, UsersYaml.class);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return users == null ? new ArrayList<>() : users.getUsers();
    }
}
