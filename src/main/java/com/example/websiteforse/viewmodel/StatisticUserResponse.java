package com.example.websiteforse.viewmodel;

public class StatisticUserResponse {
    private int totalPostCreated;
    private int totalPostLiked;
    private int totalCourseEnrolled;
    private int totalJobApply;

    public StatisticUserResponse(){}

    public int getTotalPostCreated() {
        return totalPostCreated;
    }

    public void setTotalPostCreated(int totalPostCreated) {
        this.totalPostCreated = totalPostCreated;
    }

    public int getTotalPostLiked() {
        return totalPostLiked;
    }

    public void setTotalPostLiked(int totalPostLiked) {
        this.totalPostLiked = totalPostLiked;
    }

    public int getTotalCourseEnrolled() {
        return totalCourseEnrolled;
    }

    public void setTotalCourseEnrolled(int totalCourseEnrolled) {
        this.totalCourseEnrolled = totalCourseEnrolled;
    }

    public int getTotalJobApply() {
        return totalJobApply;
    }

    public void setTotalJobApply(int totalJobApply) {
        this.totalJobApply = totalJobApply;
    }
}
