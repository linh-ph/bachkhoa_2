package com.example.websiteforse.service;

import com.example.websiteforse.dtos.PostDTO;
import com.example.websiteforse.entity.Post;

public interface IPostService {
    Post save(Post post);
    PostDTO findPostbyId(int id);
}
