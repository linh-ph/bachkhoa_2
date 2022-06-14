package com.example.websiteforse.repository;

import com.example.websiteforse.entity.Post_Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<Post_Comment, Integer> {
    List<Post_Comment> findByPost_PostId(int postid);
    List<Post_Comment> findByUser_UserId(int userid);
}
