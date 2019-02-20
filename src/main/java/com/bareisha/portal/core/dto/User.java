package com.bareisha.portal.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty
    private String name;

    @JsonProperty
    private String password;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
