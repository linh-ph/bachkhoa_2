package com.example.websiteforse.entity;

public class Discussion_Like {
    private int likeId;
    private int discussionId;
    private int userId;

    public Discussion_Like(){}

    public Discussion_Like(int likeId, int discussionId, int userId) {
        this.likeId = likeId;
        this.discussionId = discussionId;
        this.userId = userId;
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public int getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(int discussionId) {
        this.discussionId = discussionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
