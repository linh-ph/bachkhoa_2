package com.example.websiteforse.repository;

import com.example.websiteforse.entity.Subject_User;
import com.example.websiteforse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectUserRepository extends JpaRepository<Subject_User, Integer> {
    List<Subject_User> findBySubject_SubjectId(int id);

    @Query(value = "Select count(id) from Subject_User where user_id = ?", nativeQuery = true)
    int totalSubjectEnrolledByUserId(int userId);

    Subject_User findBySubject_SubjectIdAndUser_UserId(int subjectId, int userId);

    List<Subject_User> findByUser_UserId(int id);
}
