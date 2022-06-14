package com.example.websiteforse.service;

import com.example.websiteforse.entity.Subject;
import com.example.websiteforse.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl implements ISubjectService{
    @Autowired
    private SubjectRepository subjectRepo;

    @Override
    public Subject save(Subject subject) {
        return subjectRepo.save(subject);
    }
}
