package com.example.websiteforse.repository;

import com.example.websiteforse.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {
    @Query(value = "SELECT * FROM Recruitment WHERE recruitment_name LIKE %?1% ", nativeQuery = true)
    List<Recruitment> findByRecruitmentName(String name);

    List<Recruitment> findRecruitmentByCompany_CompanyId(int id);

    Recruitment findRecruitmentByRecruitmentId(int id);
}
