package com.example.websiteforse.repository;

import com.example.websiteforse.entity.Job_Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<Job_Application, Integer> {
    @Query(value = "Select count(id) from Job_Application where user_id = ?", nativeQuery = true)
    int totalJobApplicationByUserId(int userId);

    List<Job_Application> findByRecruitment_RecruitmentId(int id);
}
