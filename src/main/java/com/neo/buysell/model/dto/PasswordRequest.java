package com.neo.buysell.model.dto;

public class PasswordRequest {
    private String currentPassword;
    private String newPassword;

    public PasswordRequest() {

    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
