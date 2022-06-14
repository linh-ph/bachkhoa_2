package com.example.websiteforse.viewmodel;

public class ChangePasswordViewModel {
    private int userId;
    private String password;

    public ChangePasswordViewModel(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
