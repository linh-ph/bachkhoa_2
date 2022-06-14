package com.example.websiteforse.repository;

import com.example.websiteforse.entity.Discussion;
import com.example.websiteforse.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Integer> {
    Discussion findByDiscussionId(int ID);

    @Query(value = "SELECT * FROM Discussion WHERE discussion_title LIKE ?% ", nativeQuery = true)
    List<Discussion> findDiscussionByDiscussionTitle(String name);

    @Query(value = "SELECT * FROM Discussion WHERE subject_id = ? ", nativeQuery = true)
    List<Discussion> findDiscussionBySubjectId (int id);

    @Query(value = "SELECT * from Discussion  where subject_id = ? and discussion_title LIKE ?%", nativeQuery = true)
    List<Discussion> searchDiscussInSubjectByTitle(int id, String name);

    @Query(value = "SELECT * FROM Discussion WHERE user_id = ? ", nativeQuery = true )
    List<Discussion> searchDiscussByUseID(int id);

}
