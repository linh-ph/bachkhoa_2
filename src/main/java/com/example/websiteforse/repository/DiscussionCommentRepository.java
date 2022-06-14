package com.example.websiteforse.repository;

import com.example.websiteforse.entity.Discussion_Comment;
import com.example.websiteforse.entity.Post_Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionCommentRepository extends JpaRepository<Discussion_Comment, Integer> {
    List<Discussion_Comment> findByDiscussion_DiscussionId(int discussionid);
    List<Discussion_Comment> findByUser_UserId(int userid);
}
