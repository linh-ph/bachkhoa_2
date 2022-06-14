package com.example.websiteforse.repository;

import com.example.websiteforse.entity.Post;
import com.example.websiteforse.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findByPostId(int id);

    List<Post> findPostsByPostType_Id(int typeid);

    @Query(value = "SELECT * FROM Post WHERE post_name LIKE ?% ", nativeQuery = true)
    List<Post> findByPostName(String name);


    @Query(value = "SELECT * FROM Post WHERE  user_id = ?", nativeQuery = true)
    List<Post> getPostByUserID(int id);


    @Query(value = "Select count(user_id) from Post_Like where post_id=?", nativeQuery = true)
    int totalLike(int id);

    @Query(value = "Select count(user_id) from Post_Comment where post_id=?", nativeQuery = true)
    int totalComment(int id);

    @Query(value = "SELECT * From Post WHERE status = 2", nativeQuery = true)
    List<Post> getActivePost();

    @Query(value = "SELECT * From Post WHERE status = 1", nativeQuery = true)
    List<Post> getUnactivePost();

    @Query(value = "Select count(post_id) from Post where user_id = ?", nativeQuery = true)
    int totalPostByUserId(int userId);
}
