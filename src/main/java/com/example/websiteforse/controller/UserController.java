package com.example.websiteforse.controller;

import com.example.websiteforse.dtos.DiscussionCommentDTO;
import com.example.websiteforse.dtos.JobApplicationDTO;
import com.example.websiteforse.dtos.PostCommentDTO;
import com.example.websiteforse.dtos.UserDTO;
import com.example.websiteforse.entity.*;
import com.example.websiteforse.repository.*;
import com.example.websiteforse.service.AccountServiceImpl;
import com.example.websiteforse.service.UserServiceImpl;
import com.example.websiteforse.viewmodel.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private PostCommentRepository postCommentRepo;

    @Autowired
    private JobApplicationRepository jobAppRepo;

    @Autowired
    private DiscussionCommentRepository discussionCommentRepo;

    @Autowired
    private LikePostRepository likePostRepo;
    /*@PostMapping("/login")
    public AccountViewModel login(@RequestBody AccountLoginViewModel model){
        Account dto = accountService.login(model.getUsername(), model.getPassword());
        AccountViewModel entity = new AccountViewModel();
        entity.setUsername(dto.getUsername());
        entity.setRoleId(dto.getRole().getRoleId());
        return entity;
    }*/

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountLoginViewModel model){
        Account dto = accountService.login(model.getUsername(), model.getPassword());
        LoginResponse response = new LoginResponse();
        User user = userRepo.findUser(dto.getUsername());
        if(user != null){
            response.setUserId(user.getUserId());
            response.setEmail(user.getEmail());
            response.setImage(user.getImage());
            response.setName(user.getName());
            response.setRoleId(dto.getRole().getRoleId());
            response.setUsername(dto.getUsername());
        }
        else{
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.ok().body(response);
    }

    // Create account
    @PostMapping("/add")
    public ResponseEntity<?> createAccount(@RequestBody UserAccountViewModel model){
        User userExisted = userRepo.findUser(model.getUsername());
        if(userExisted == null){
            Account account = new Account();
            User user = new User();
            Role role = roleRepo.findByRoleId(model.getRoleId());
            account.setUsername(model.getUsername());
            account.setPassword(model.getPassword());
            account.setRole(role);
            accountRepo.save(account);
            user.setName(model.getName());
            user.setImage(model.getImage());
            user.setEmail(model.getEmail());
            user.setUsername(account);
            userRepo.save(user);
            return ResponseEntity.ok("Create account success!!!");
        }
        else{
            return ResponseEntity.badRequest().body("Username already existed.");
        }
    }

    // Delete Account + set Username in User null
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(@RequestParam(value = "username") String name){
        userService.deleteUsername(name);
        return ResponseEntity.ok("Delete success");
    }

    // Delete user including comments and likes
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id){
        List<Post_Like> likes = likePostRepo.findByUser_UserId(id);
        List<Post_Comment> postComments = postCommentRepo.findByUser_UserId(id);
        List<Discussion_Comment> discussionComments = discussionCommentRepo.findByUser_UserId(id);
        for(Post_Like like : likes){
            likePostRepo.deleteById(like.getLikeId());
        }
        for(Post_Comment postcomment : postComments){
            postCommentRepo.deleteById(postcomment.getCommentId());
        }
        for (Discussion_Comment discussioncomment : discussionComments){
            discussionCommentRepo.deleteById(discussioncomment.getCommentId());
        }
        userRepo.deleteById(id);
        return ResponseEntity.ok("Delete success");
    }

    // Edit User
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserAccountViewModel model, @PathVariable("id") int id){
        User user = userRepo.findByUserId(id);
        // Account account = accountRepo.findAccountByUsername(user.getUsername().getUsername());
        if(user != null){
            user.setImage(model.getImage());
            user.setName(model.getName());
            user.setEmail(model.getEmail());
            /*account.setPassword(model.getPassword());
            accountRepo.save(account);*/
            userRepo.save(user);
            return ResponseEntity.ok("Update user success !!!");
        }
        else{
            return ResponseEntity.badRequest().body("Not found user id");
        }
    }
    @GetMapping("/getByID/{id}")
    public UserDTO getUserByID(@PathVariable("id") int id)
    {
        User user = userRepo.findByUserId(id);
        UserDTO dto = new UserDTO();
        if (user != null){
            dto.setUserId(user.getUserId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setImage(user.getImage());
        }
        return dto;
    }
    @GetMapping("/getByName/{name}")
    public List<UserDTO> getUserByName(@PathVariable("name") String name){
        List<User> user = userRepo.findUserByName(name);
        List<UserDTO> dtos = new ArrayList<>();
        if (user != null){
            for (int i = 0; i < user.size(); i++) {
                UserDTO dto = new UserDTO();
                dto.setUserId(user.get(i).getUserId());
                dto.setName(user.get(i).getName());
                dto.setEmail(user.get(i).getEmail());
                dto.setImage(user.get(i).getImage());
                dtos.add(dto);
            }

        }
        return dtos;
    }
    @PostMapping("/commentPost")
    public ResponseEntity<?> addComment(@RequestBody PostCommentDTO dto){
        Post_Comment postComment = new Post_Comment();
        Post post = new Post();
        User user = new User();
        post.setPostId(dto.getPostId());
        user.setUserId(dto.getUserId());
        postComment.setPost(post);
        postComment.setUser(user);
        postComment.setCommentContent(dto.getContent());
        postCommentRepo.save(postComment);
        return ResponseEntity.ok("Add comment success");
    }

    @DeleteMapping("/deleteComment/{commentID}/post")
    public ResponseEntity<?> deleteCommentPost(@PathVariable("commentID") int commentID){
        postCommentRepo.deleteById(commentID);
        return ResponseEntity.ok("Delete comment success");
    }

    @PutMapping("/edit/commentPost/{id}")
    public ResponseEntity<?> editCommentPost(@RequestBody PostCommentDTO dto, @PathVariable("id") int id){
        Post_Comment postComment = postCommentRepo.getById(id);
        if(postComment != null){
            postComment.setCommentContent(dto.getContent());
            postCommentRepo.save(postComment);
        }
        else{
            return ResponseEntity.badRequest().body("Comment id not found");
        }
        return ResponseEntity.ok("Edit comment success");
    }
    @GetMapping("/getAllUser")
    public List<UserDTO> GetAllUser(){
        List<UserDTO>  dtos =  new ArrayList<>();
        List <User> users = userRepo.findAll();
        for (int i = 0; i < users.size(); i++) {
            UserDTO dto = new UserDTO();
            dto.setUserId(users.get(i).getUserId());
            dto.setName(users.get(i).getName());
            dto.setEmail(users.get(i).getEmail());
            dto.setImage(users.get(i).getImage());
            dtos.add(dto);
        }
        return dtos;
    }

    // Apply job
    @PostMapping("/applyjob")
    public ResponseEntity<?> applyJob(@RequestBody JobApplicationDTO request){
        Job_Application jobApp = new Job_Application();
        User user = new User();
        user.setUserId(request.getUserId());
        Recruitment recruitment = new Recruitment();
        recruitment.setRecruitmentId(request.getRecruitmentId());
        jobApp.setUser(user);
        jobApp.setRecruitment(recruitment);
        Date date = new Date(new java.util.Date().getTime());
        jobApp.setDateCreated(date);
        jobApp.setCv_url(request.getCvUrl());
        jobApp.setEmail(request.getEmail());
        jobApp.setPhoneNumber(request.getPhoneNumber());
        jobAppRepo.save(jobApp);
        return ResponseEntity.ok("Apply job success!!");
    }

    @GetMapping("/getAllLecturer")
    public ResponseEntity<?> getAllLecturer(){
        List<GetUserViewModel> lecturers = new ArrayList<>();
        List<Account> accounts = accountRepo.getAllLecturer();
        for(Account entity : accounts){
            User user = userRepo.findUser(entity.getUsername());
            if(user != null){
                GetUserViewModel lecturer = new GetUserViewModel();
                lecturer.setUserId(user.getUserId());
                lecturer.setName(user.getName());
                lecturers.add(lecturer);
            }
        }
        return ResponseEntity.ok(lecturers);
    }

    // add comment to discussion
    @PostMapping("/commentDiscussion")
    public ResponseEntity<?> addCommentDiscussion(@RequestBody DiscussionCommentDTO dto){
        Discussion_Comment discussionComment = new Discussion_Comment();
        Discussion discussion = new Discussion();
        User user = new User();
        discussion.setDiscussionId(dto.getDiscussionId());
        user.setUserId(dto.getUserId());
        discussionComment.setDiscussion(discussion);
        discussionComment.setUser(user);
        discussionComment.setCommentContent(dto.getContent());
        discussionCommentRepo.save(discussionComment);
        return ResponseEntity.ok("Add comment success");
    }

    @DeleteMapping("/deleteComment/{commentID}/discussion")
    public ResponseEntity<?> deleteCommentDiscussion(@PathVariable("commentID") int commentID){
        discussionCommentRepo.deleteById(commentID);
        return ResponseEntity.ok("Delete comment success");
    }

    @PutMapping("/edit/commentDiscussion/{id}")
    public ResponseEntity<?> editCommentDiscussion(@RequestBody DiscussionCommentDTO dto, @PathVariable("id") int id){
        Discussion_Comment discussionComment = discussionCommentRepo.getById(id);
        if(discussionComment != null){
            discussionComment.setCommentContent(dto.getContent());
            discussionCommentRepo.save(discussionComment);
        }
        else{
            return ResponseEntity.badRequest().body("Comment id not found");
        }
        return ResponseEntity.ok("Edit comment success");
    }

    @GetMapping("/getUserInfoById/{id}")
    public ResponseEntity<?> getUserInfoById(@PathVariable("id") int id){
        User user = userRepo.findByUserId(id);
        Account account = accountRepo.findAccountByUsername(user.getUsername().getUsername());
        UserInfoViewModel userinfo = new UserInfoViewModel();
        userinfo.setUserId(user.getUserId());
        userinfo.setEmail(user.getEmail());
        userinfo.setUsername(account.getUsername());
        userinfo.setPassword(account.getPassword());
        userinfo.setName(user.getName());
        userinfo.setImage(user.getImage());
        return ResponseEntity.ok(userinfo);
    }

    @PutMapping("/changepassword/{id}")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordViewModel model, @PathVariable("id") int id){
        User user = userRepo.findByUserId(id);
        Account account = accountRepo.findAccountByUsername(user.getUsername().getUsername());
        if(user != null){
            account.setPassword(model.getPassword());
            accountRepo.save(account);
            userRepo.save(user);
            return ResponseEntity.ok("Change password success !!!");
        }
        else{
            return ResponseEntity.badRequest().body("Not found user id");
        }
    }
}
