package com.example.websiteforse.repository;

import com.example.websiteforse.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findByCompanyId(int id);
    Company findByUser_UserId(int id);
}
