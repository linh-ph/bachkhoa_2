package com.example.websiteforse.service;

import com.example.websiteforse.dtos.PostDTO;
import com.example.websiteforse.dtos.SubjectDTO;
import com.example.websiteforse.dtos.UserDTO;
import com.example.websiteforse.entity.Post;
import com.example.websiteforse.entity.Subject;
import com.example.websiteforse.entity.User;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    public PostDTO toPostDTO(Post entity){
        PostDTO dto = new PostDTO();
        dto.setPostId(entity.getPostId());
        dto.setPostName(entity.getPostName());
        dto.setPostContent(entity.getPostContent());
        dto.setPostImage(entity.getPostImage());
        dto.setPostType(entity.getPostType().getId());
        dto.setPostDate(entity.getPostDate());
        dto.setUserId(entity.getUser().getUserId());
        return dto;
    }

    public UserDTO toUserDTO(User entity){
        UserDTO dto = new UserDTO();
        dto.setUserId(entity.getUserId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setImage(entity.getImage());
        return dto;
    }

    public SubjectDTO toSubjectDTO(Subject entity){
        SubjectDTO dto = new SubjectDTO();
        dto.setSubjectId(entity.getSubjectId());
        dto.setUserId(entity.getUser().getUserId());
        dto.setSubjectName(entity.getSubjectName());
        dto.setSubjectDescription(entity.getSubjectDescription());
        dto.setSubjectImage(entity.getSubjectImage());
        dto.setPassword(entity.getPassword());
        return dto;
    }
}
