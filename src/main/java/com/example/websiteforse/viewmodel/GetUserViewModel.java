package com.example.websiteforse.viewmodel;

public class GetUserViewModel {
    private int userId;
    private String name;

    public GetUserViewModel(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
