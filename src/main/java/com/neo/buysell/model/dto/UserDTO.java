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

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.firstName);
        user.setLastName(userDTO.lastName);
        user.setEmail(userDTO.email);
        user.setPhone(userDTO.phone);
        user.setAvatarPath(userDTO.avatarPath);
        return user;
    }

    public static UserDTO toDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.id = user.getId();
        userDTO.firstName = user.getFirstName();
        userDTO.lastName = user.getLastName();
        userDTO.email = user.getEmail();
        userDTO.phone = user.getPhone();
        userDTO.avatarPath = user.getAvatarPath();
        return userDTO;
    }

}