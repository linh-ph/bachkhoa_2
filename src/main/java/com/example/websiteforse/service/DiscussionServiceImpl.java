package com.example.websiteforse.service;

import com.example.websiteforse.entity.Discussion;
import com.example.websiteforse.repository.DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscussionServiceImpl implements IDiscussionService{
    @Autowired
    private DiscussionRepository discussionRepository;

    @Override
    public Discussion save(Discussion discussion){
        return discussionRepository.save(discussion);
    }

}
