package com.example.websiteforse.viewmodel;

public class AccountViewModel {
    private String username;
    private int roleId;

    public AccountViewModel(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
