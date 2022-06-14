package com.example.websiteforse.viewmodel;

public class LikePostViewModel {
    private int postId;
    private int userId;

    public LikePostViewModel(){}

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
