package com.bareisha.portal.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UsersYaml {
    @JsonProperty
    private List<User> users;

    public UsersYaml() {
    }

    public UsersYaml(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
