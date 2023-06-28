package com.neo.buysell.model.dto;

public class TLogin {
    private String username;
    private String password;

    public TLogin() {

    }

    public TLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
