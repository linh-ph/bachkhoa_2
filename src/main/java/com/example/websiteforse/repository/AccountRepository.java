package com.example.websiteforse.repository;

import com.example.websiteforse.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    <Optional>Account findByUsernameAndPassword(String username, String password);

    Account findAccountByUsername(String username);
    @Modifying
    @Query(value = "Delete from Account where username=?",nativeQuery = true)
    void deleteAccount(String username);

    @Query(value = "Select * from Account where role_id = 3", nativeQuery = true)
    List<Account> getAllLecturer();
}
