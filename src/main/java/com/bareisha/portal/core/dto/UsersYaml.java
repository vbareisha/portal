package com.bareisha.portal.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UsersYaml {
    @JsonProperty
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }
}
