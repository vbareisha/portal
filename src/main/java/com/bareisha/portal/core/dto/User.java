package com.bareisha.portal.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {

    @JsonProperty
    private String name;

    @JsonProperty
    private String password;

    @JsonProperty
    private String roomNumber;

    @JsonProperty
    private Boolean active = false;

    public User() {
    }

    public User(String name, String password, String roomNumber) {
        this.name = name;
        this.password = password;
        this.roomNumber = roomNumber;
    }
}
