package com.example.websiteforse.repository;

import com.example.websiteforse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(int id);

    @Query(value = "Select * from Users where username LIKE %?",nativeQuery = true)
    List<User> findUserByName(String username);

    @Query(value = "Select * from Users where username=?",nativeQuery = true)
    User findUser(String username);

}
