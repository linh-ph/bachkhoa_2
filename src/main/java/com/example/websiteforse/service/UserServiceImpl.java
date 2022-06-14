package com.example.websiteforse.service;

import com.example.websiteforse.entity.User;
import com.example.websiteforse.repository.AccountRepository;
import com.example.websiteforse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public void deleteUsername(String name) {
        User user = userRepo.findUser(name);
        user.setUsername(null);
        userRepo.save(user);
        accountRepo.deleteAccount(name);
    }
}
