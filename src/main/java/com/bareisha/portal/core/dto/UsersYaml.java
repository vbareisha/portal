package com.bareisha.portal.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UsersYaml {
    @JsonProperty
    private List<User> users;

    public UsersYaml() {
    }

    public UsersYaml(List<User> users) {
        this.users = users;
    }

}
