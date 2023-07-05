package com.neo.buysell.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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