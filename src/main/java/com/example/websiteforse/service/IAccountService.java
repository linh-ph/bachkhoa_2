package com.example.websiteforse.service;

import com.example.websiteforse.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAccountService {
    Account login(String username, String password);
    List<Account> getAll();
}
