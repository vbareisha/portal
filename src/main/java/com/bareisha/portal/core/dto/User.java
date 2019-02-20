package com.bareisha.portal.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty
    private String name;

    @JsonProperty
    private String password;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
