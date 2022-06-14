package com.example.websiteforse.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Discussion")
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discussion_id")
    private int discussionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "discussion_title")
    private String discussionTitle;

    @Column(name = "discussion_content")
    private String discussionContent;

    @Column(name = "status")
    private int status;

    @Column(name = "time_created")
    private Date discussDate;

    public Discussion(){}

    public Discussion(int discussionId, Subject subject, User user, String discussionTitle, String discussionContent, int status, Date discussDate) {
        this.discussionId = discussionId;
        this.subject = subject;
        this.user = user;
        this.discussionTitle = discussionTitle;
        this.discussionContent = discussionContent;
        this.status = status;
        this.discussDate = discussDate;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDiscussDate(Date discussDate) {
        this.discussDate = discussDate;
    }

    public int getStatus() {
        return status;
    }

    public Date getDiscussDate() {
        return discussDate;
    }

    public int getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(int discussionId) {
        this.discussionId = discussionId;
    }

    public String getDiscussionTitle() {
        return discussionTitle;
    }

    public void setDiscussionTitle(String discussionTitle) {
        this.discussionTitle = discussionTitle;
    }

    public String getDiscussionContent() {
        return discussionContent;
    }

    public void setDiscussionContent(String discussionContent) {
        this.discussionContent = discussionContent;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
