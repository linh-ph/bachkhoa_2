package com.example.websiteforse.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "post_Name")
    private String postName;

    @Column(name = "post_date")
    private Date postDate;

    @Column(name = "post_content")
    private String postContent;

    @Column(name = "post_image")
    private String postImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "type_id")
    private PostType postType;

    @Column(name = "status")
    private int status;

    public Post(){}

    public Post(int postId, User user, String postName, Date postDate, String postContent, String postImage, PostType postType, int status) {
        this.postId = postId;
        this.user = user;
        this.postName = postName;
        this.postDate = postDate;
        this.postContent = postContent;
        this.postImage = postImage;
        this.postType = postType;
        this.status = status;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }
}
