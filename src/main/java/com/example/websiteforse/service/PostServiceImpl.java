package com.example.websiteforse.service;

import com.example.websiteforse.dtos.PostDTO;
import com.example.websiteforse.entity.Post;
import com.example.websiteforse.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements IPostService{
    @Autowired
    private PostRepository postRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public PostDTO findPostbyId(int id) {
        return null;
    }
}
