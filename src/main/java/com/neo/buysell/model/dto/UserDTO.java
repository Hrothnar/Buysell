package com.neo.buysell.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.buysell.model.entity.User;

public class UserDTO {
    public long id;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    @JsonProperty(value = "image")
    public String avatarPath;

    public UserDTO() {

    }

}