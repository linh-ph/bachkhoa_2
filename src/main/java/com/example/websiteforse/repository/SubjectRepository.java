package com.example.websiteforse.repository;

import com.example.websiteforse.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Subject findBySubjectId(int id);

    @Query(value = "SELECT * FROM Subject WHERE subject_name LIKE ?% ", nativeQuery = true)
    List<Subject> findBySubjectName(String name);


    @Query(value = "SELECT * FROM Subject WHERE  user_id = ?", nativeQuery = true)
    List<Subject> getSubjectByUserID(int id);

    //@Query(value = "SELECT * FROM Subject WHERE subject_id = ? and password = ?", nativeQuery = true)
    <Optional>Subject findBySubjectIdAndAndPassword(int subjectId, String password);
}
