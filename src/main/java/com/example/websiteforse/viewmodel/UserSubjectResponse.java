package com.example.websiteforse.viewmodel;

import com.example.websiteforse.dtos.SubjectDTO;
import com.example.websiteforse.dtos.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserSubjectResponse {
    private UserDTO user;
    private List<SubjectDTO> listSubject = new ArrayList<>();

    public UserSubjectResponse(){}

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<SubjectDTO> getListSubject() {
        return listSubject;
    }

    public void setListSubject(List<SubjectDTO> listSubject) {
        this.listSubject = listSubject;
    }
}
