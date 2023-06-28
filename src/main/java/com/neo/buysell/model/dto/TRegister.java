package com.neo.buysell.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neo.buysell.model.enumeration.Role;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class TRegister {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

    public TRegister() {

    }

    public TRegister(String username, String password, String firstName, String lastName, String phone, Role role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }
}
