package com.example.websiteforse.repository;

import com.example.websiteforse.entity.Post_Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikePostRepository extends JpaRepository<Post_Like, Integer> {
    Post_Like findByPost_PostIdAndUser_UserId(int postid, int userid);

    @Query(value = "Select count(like_id) from Post_Like where user_id = ?", nativeQuery = true)
    int totalLikeByUserId(int userId);

    List<Post_Like> findByPost_PostId(int id);

    List<Post_Like> findByUser_UserId(int id);
}
