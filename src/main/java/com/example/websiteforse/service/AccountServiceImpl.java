package com.example.websiteforse.service;

import com.example.websiteforse.entity.Account;
import com.example.websiteforse.repository.AccountRepository;
import com.example.websiteforse.repository.RoleRepository;
import com.example.websiteforse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public Account login(String username, String password) {
        Account dto = accountRepo.findByUsernameAndPassword(username,password);
        return dto;
    }

    @Override
    public List<Account> getAll() {
        return accountRepo.findAll();
    }
}
