package com.example.websiteforse.controller;

import com.example.websiteforse.repository.*;
import com.example.websiteforse.viewmodel.StatisticUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/statistic")
public class StatisticController {
    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LikePostRepository likePostRepo;

    @Autowired
    private SubjectUserRepository subjectUserRepo;

    @Autowired
    private JobApplicationRepository jobAppRepo;

    @Autowired
    private SubjectRepository subjectRepo;

    @GetMapping("/{userid}")
    public ResponseEntity<?> statisticUser(@PathVariable("userid") int userId){
        StatisticUserResponse response = new StatisticUserResponse();
        response.setTotalPostLiked(likePostRepo.totalLikeByUserId(userId));
        response.setTotalJobApply(jobAppRepo.totalJobApplicationByUserId(userId));
        response.setTotalPostCreated(postRepo.totalPostByUserId(userId));
        response.setTotalCourseEnrolled(subjectUserRepo.totalSubjectEnrolledByUserId(userId));
        return ResponseEntity.ok(response);
    }
}
