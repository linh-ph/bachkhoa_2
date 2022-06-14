package com.example.websiteforse.service;

import com.example.websiteforse.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    User save(User user);
    void deleteUsername(String name);
}
