package com.neo.buysell.model.dto;

import com.neo.buysell.model.entity.User;

public class UserUpdaterDTO {
    public String firstName;
    public String lastName;
    public String phone;

    public UserUpdaterDTO() {

    }

    public static User toEntity(UserUpdaterDTO userUpdaterDTO) {
        User user = new User();
        user.setFirstName(userUpdaterDTO.firstName);
        user.setLastName(userUpdaterDTO.lastName);
        user.setPhone(userUpdaterDTO.phone);
        return user;
    }

    public static UserUpdaterDTO toDto(User user) {
        UserUpdaterDTO userUpdaterDTO = new UserUpdaterDTO();
        userUpdaterDTO.firstName = user.getFirstName();
        userUpdaterDTO.lastName = user.getLastName();
        userUpdaterDTO.phone = user.getPhone();
        return userUpdaterDTO;
    }

}
