package com.example.websiteforse.viewmodel;

public class UserAccountViewModel {
    private int userId;
    private String username;
    private String password;
    private int roleId;
    private String name;
    private String image;
    private String email;

    public UserAccountViewModel(){}

    public UserAccountViewModel(int userId, String username, String password, int roleId, String name, String image, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.name = name;
        this.image = image;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
