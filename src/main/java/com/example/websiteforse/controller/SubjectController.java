package com.example.websiteforse.controller;

import com.example.websiteforse.dtos.PostDTO;
import com.example.websiteforse.dtos.SubjectDTO;
import com.example.websiteforse.dtos.UserDTO;
import com.example.websiteforse.entity.*;
import com.example.websiteforse.repository.*;
import com.example.websiteforse.service.Converter;
import com.example.websiteforse.service.SubjectServiceImpl;
import com.example.websiteforse.service.UserServiceImpl;
import com.example.websiteforse.viewmodel.CreateSubjectViewModel;
import com.example.websiteforse.viewmodel.EnrollSubject;
import com.example.websiteforse.viewmodel.UnenrollSubject;
import com.example.websiteforse.viewmodel.UserSubjectResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    @Autowired
    private SubjectServiceImpl subjectService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SubjectUserRepository subjectUserRepo;

    @Autowired
    private SubjectRepository subjectRepo;

    @Autowired
    private Converter converter;

    @Autowired
    private DiscussionRepository discussRepo;

    @Autowired
    private DiscussionCommentRepository discussCommentRepo;

    @GetMapping("/getall")
    public List<SubjectDTO> showAllSubject(){
        List<SubjectDTO> dtos = new ArrayList<>();
        List<Subject> subjects = subjectRepo.findAll();
        for (Subject subject: subjects){
            SubjectDTO dto = converter.toSubjectDTO(subject);
            List<UserDTO> userDTOList = new ArrayList<>();
            List<Subject_User> subjectUserList = subjectUserRepo.findBySubject_SubjectId(dto.getSubjectId());
            if(subjectUserList != null){
                for(Subject_User subUser: subjectUserList){
                    User user = subUser.getUser();
                    UserDTO userDTO = converter.toUserDTO(user);
                    userDTOList.add(userDTO);
                }
            }
            dto.setStudents(userDTOList);
            dtos.add(dto);
        }
        return dtos;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSubject(@RequestBody CreateSubjectViewModel request){
        Subject subject = new Subject();
        User user = new User();
        user.setUserId(request.getUserId());
        subject.setUser(user);
        subject.setSubjectName(request.getSubjectName());
        subject.setSubjectDescription(request.getSubjectDescription());
        subject.setSubjectImage(request.getSubjectImage());
        subject.setPassword(request.getPassword());
        subjectService.save(subject);
        return ResponseEntity.ok("Add success");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable("id") int id) {
        List<Discussion> discussions = discussRepo.findDiscussionBySubjectId(id);
        List<Subject_User> users = subjectUserRepo.findBySubject_SubjectId(id);
        for(Discussion discuss : discussions){
            List<Discussion_Comment> comments = discussCommentRepo.findByDiscussion_DiscussionId(discuss.getDiscussionId());
            for(Discussion_Comment comment : comments){
                discussCommentRepo.deleteById(comment.getCommentId());
            }
            discussRepo.deleteById(discuss.getDiscussionId());
        }
        for(Subject_User user : users){
            subjectUserRepo.deleteById(user.getId());
        }
        subjectRepo.deleteById(id);
        return ResponseEntity.ok("Delete success ");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateSubject(@RequestBody SubjectDTO dto, @PathVariable("id") int id){
        Subject subject = subjectRepo.findBySubjectId(id);
        if(subject != null){
            User user = new User();
            user.setUserId(dto.getUserId());
            subject.setUser(user);
            subject.setSubjectName(dto.getSubjectName());
            subject.setSubjectDescription(dto.getSubjectDescription());
            subject.setSubjectImage(dto.getSubjectImage());
            subject.setPassword(dto.getPassword());
            List<Subject_User> listSubjectUser = subjectUserRepo.findBySubject_SubjectId(id);
            subjectUserRepo.deleteAll(listSubjectUser);
            for(UserDTO userDTO: dto.getStudents()){
                User user1 = new User();
                user1.setUserId(userDTO.getUserId());
                Subject_User subUser = new Subject_User();
                subUser.setUser(user1);
                subUser.setSubject(subject);
                subjectUserRepo.save(subUser);
            }
            subjectRepo.save(subject);
            return ResponseEntity.ok("Update subject success");
        }
        else{
            return ResponseEntity.badRequest().body("Not found subject id");
        }
    }
    @GetMapping("/getById/{id}")
    public SubjectDTO getSubjectByID(@PathVariable("id") int id){
        Subject subject = subjectRepo.findBySubjectId(id);
        SubjectDTO dto = new SubjectDTO();
        if(subject != null){
            dto = converter.toSubjectDTO(subject);
            List<UserDTO> studentDTOList = new ArrayList<>();
            List<Subject_User> subjectUserList = subjectUserRepo.findBySubject_SubjectId(dto.getSubjectId());
            if(subjectUserList != null){
                for(Subject_User subUser: subjectUserList){
                    User user = subUser.getUser();
                    UserDTO userDTO = converter.toUserDTO(user);
                    studentDTOList.add(userDTO);
                }
            }
            dto.setStudents(studentDTOList);

        }
        return dto;
    }
    @GetMapping("/getSubjectByUserId/{id}")
    public List<SubjectDTO> getSubjectByUserID(@PathVariable("id") int userId){

        List<Subject> subjects = subjectRepo.getSubjectByUserID(userId);
        List<SubjectDTO> dtoListSubject = new ArrayList<>();
        if(subjects != null){
            for (int i = 0; i <subjects.size(); i++){
                SubjectDTO dtos = new SubjectDTO();
                dtos = converter.toSubjectDTO(subjects.get(i));
                List<UserDTO> studentDTOList = new ArrayList<>();
                List<Subject_User> subjectUserList = subjectUserRepo.findBySubject_SubjectId(dtos.getSubjectId());
                if(subjectUserList != null){
                    for(Subject_User subUser: subjectUserList){
                        User user = subUser.getUser();
                        UserDTO userDTO = converter.toUserDTO(user);
                        studentDTOList.add(userDTO);
                    }
                }
                dtos.setStudents(studentDTOList);
                dtoListSubject.add(dtos);
            }

        }
        return dtoListSubject;
    }
    @GetMapping("/getByName/{name}")
    public List<SubjectDTO> getSubjectByName(@PathVariable("name") String name){
        List<Subject> subjects = subjectRepo.findBySubjectName(name);
        List<SubjectDTO> dtoListSubject = new ArrayList<>();
        if(subjects != null){
            for (int i = 0; i <subjects.size(); i++){
                SubjectDTO dtos = new SubjectDTO();
                dtos = converter.toSubjectDTO(subjects.get(i));
                List<UserDTO> studentDTOList = new ArrayList<>();
                List<Subject_User> subjectUserList = subjectUserRepo.findBySubject_SubjectId(dtos.getSubjectId());
                if(subjectUserList != null){
                    for(Subject_User subUser: subjectUserList){
                        User user = subUser.getUser();
                        UserDTO userDTO = converter.toUserDTO(user);
                        studentDTOList.add(userDTO);
                    }
                }
                dtos.setStudents(studentDTOList);
                dtoListSubject.add(dtos);
            }

        }
        return dtoListSubject;
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enrollSubject(@RequestBody EnrollSubject request){
        Subject subject = subjectRepo.findBySubjectIdAndAndPassword(request.getSubjectId(), request.getPassword());
        Subject_User subUser = new Subject_User();
        if(subject != null){
            User user = new User();
            user.setUserId(request.getUserId());
            subUser.setSubject(subject);
            subUser.setUser(user);
            Subject_User existed = subjectUserRepo.findBySubject_SubjectIdAndUser_UserId(request.getSubjectId(), request.getUserId());
            if(existed != null){
                return ResponseEntity.ok("Student has enrolled.");
            }
            else{
                subjectUserRepo.save(subUser);
                return ResponseEntity.ok("Enroll success");
            }
        }
        else{
            return ResponseEntity.badRequest().body("Not found subject or wrong password");
        }
    }

    /*
    @PutMapping("/unenroll")
    public ResponseEntity<?> unenrollSubject(@RequestBody UnenrollSubject request){
        return ResponseEntity.ok("Unenroll success");
    }
     */

    @GetMapping("/getAllSubjectByUserId/{id}")
    public ResponseEntity<?> getAllSubjectByUserId(@PathVariable("id") int userId){
        UserSubjectResponse response = new UserSubjectResponse();
        List<Subject_User> subjectUsers = subjectUserRepo.findByUser_UserId(userId);
        List<SubjectDTO> subjectDtos = new ArrayList<>();
        for (Subject_User entity : subjectUsers){
            SubjectDTO subDto = new SubjectDTO();
            subDto.setSubjectId(entity.getSubject().getSubjectId());
            subDto.setSubjectImage(entity.getSubject().getSubjectImage());
            subDto.setSubjectName(entity.getSubject().getSubjectName());
            subDto.setSubjectDescription(entity.getSubject().getSubjectDescription());
            subDto.setUserId(entity.getUser().getUserId());

            List<UserDTO> userDTOList = new ArrayList<>();
            List<Subject_User> subjectUserList = subjectUserRepo.findBySubject_SubjectId(entity.getSubject().getSubjectId());
            if(subjectUserList != null){
                for(Subject_User subUser: subjectUserList){
                    User user = subUser.getUser();
                    UserDTO userDTO = converter.toUserDTO(user);
                    userDTOList.add(userDTO);
                }
            }
            subDto.setStudents(userDTOList);

            subjectDtos.add(subDto);
        }
        response.setListSubject(subjectDtos);
        User user = userRepo.findByUserId(userId);
        UserDTO userDto = new UserDTO();
        if(user != null){
            userDto.setUserId(user.getUserId());
            userDto.setImage(user.getImage());
            userDto.setEmail(user.getEmail());
            userDto.setName(user.getName());
        }
        response.setUser(userDto);
        return ResponseEntity.ok(response);
    }
}
