package com.neo.buysell.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.buysell.model.entity.User;
import com.neo.buysell.model.util.Paths;

public class UserDTO {
    public long id;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    @JsonProperty(value = "image")
    public String avatarPath;

    public static UserDTO toUserDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.id = user.getId();
        userDTO.email = user.getEmail();
        userDTO.firstName = user.getFirstName();
        userDTO.lastName = user.getLastName();
        userDTO.phone = user.getPhone();
        userDTO.avatarPath = Paths.GET_AVATAR_ENDPOINT;
        return userDTO;
    }


}