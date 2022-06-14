package com.example.websiteforse.controller;

import com.example.websiteforse.dtos.PostCommentDTO;
import com.example.websiteforse.dtos.PostDTO;
import com.example.websiteforse.dtos.PostTypeDTO;
import com.example.websiteforse.entity.*;
import com.example.websiteforse.repository.LikePostRepository;
import com.example.websiteforse.repository.PostCommentRepository;
import com.example.websiteforse.repository.PostRepository;
import com.example.websiteforse.repository.PostTypeRepository;
import com.example.websiteforse.service.PostServiceImpl;
import com.example.websiteforse.viewmodel.LikePostViewModel;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostRepository postRepo;

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private PostTypeRepository postTypeRepo;

    @Autowired
    private LikePostRepository likePostRepo;

    @Autowired
    private PostCommentRepository postCommentRepo;

    @GetMapping("/getall")
    public List<PostDTO> showAllPost(){
        List<Post> posts = postRepo.findAll();
        List<PostDTO> dtos = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++){
            PostDTO dto = new PostDTO();
            dto.setPostId(posts.get(i).getPostId());
            dto.setPostName(posts.get(i).getPostName());
            dto.setPostDate(posts.get(i).getPostDate());
            dto.setPostContent(posts.get(i).getPostContent());
            dto.setPostImage(posts.get(i).getPostImage());
            dto.setPostType(posts.get(i).getPostType().getId());
            dto.setUserId(posts.get(i).getUser().getUserId());
            dto.setStatus(posts.get(i).getStatus());
            dto.setTotalComment(postRepo.totalComment(dto.getPostId()));
            dto.setTotalLike(postRepo.totalLike(dto.getPostId()));
            dtos.add(dto);
        }
        return dtos;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPost(@RequestBody PostDTO request){
        Post post = new Post();
        User user = new User();
        PostType postType = new PostType();
        postType.setId(request.getPostType());
        user.setUserId(request.getUserId());
        post.setUser(user);
        post.setPostContent(request.getPostContent());
        post.setPostImage(request.getPostImage());
        post.setPostName(request.getPostName());
        post.setPostType(postType);
        post.setStatus(1);
        Date date = new Date(new java.util.Date().getTime());
        post.setPostDate(date);
        postService.save(post);
        return ResponseEntity.ok("Add success!!!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") int id) {
        List<Post_Comment> comments = postCommentRepo.findByPost_PostId(id);
        for(Post_Comment comment : comments){
            postCommentRepo.deleteById(comment.getCommentId());
        }
        List<Post_Like> likes = likePostRepo.findByPost_PostId(id);
        for (Post_Like like : likes){
            likePostRepo.deleteById(like.getLikeId());
        }
        postRepo.deleteById(id);
        return ResponseEntity.ok("Delete success !!!");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updatePost(@RequestBody PostDTO dto,@PathVariable("id") int id){
        Post post = postRepo.findByPostId(id);
        if(post != null){
            post.setPostName(dto.getPostName());
            post.setPostImage(dto.getPostImage());
            post.setPostContent(dto.getPostContent());
            postService.save(post);
            return ResponseEntity.ok("Update post success !!!");
        }
        else{
            return ResponseEntity.badRequest().body("Not found post id");
        }
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approvePost(@PathVariable("id") int id, @RequestBody PostDTO dto){
        Post post = postRepo.findByPostId(id);
        if(post != null){
            post.setStatus(dto.getStatus());
            postRepo.save(post);
            return ResponseEntity.ok("Status post change success");
        }
        else{
            return ResponseEntity.badRequest().body("Not found post id" +id);
        }
    }

    @GetMapping("/get/{id}")
    public PostDTO getPostByID(@PathVariable("id") int id){
        Post post = postRepo.findByPostId(id);
        PostDTO dto = new PostDTO();
        if(post != null){
            dto.setPostId(post.getPostId());
            dto.setPostName(post.getPostName());
            dto.setPostDate(post.getPostDate());
            dto.setPostContent(post.getPostContent());
            dto.setPostImage(post.getPostImage());
            dto.setPostType(post.getPostType().getId());
            dto.setUserId(post.getUser().getUserId());
            dto.setStatus(post.getStatus());
            dto.setTotalComment(postRepo.totalComment(dto.getPostId()));
            dto.setTotalLike(postRepo.totalLike(dto.getPostId()));
        }
        return dto;
    }

    // Get filter Blog or Guideline by Post_Type id
    @GetMapping("/getByType/{id}")
    public List<PostDTO> getPostsByType(@PathVariable("id") int type){
        List<PostDTO> dtos = new ArrayList<>();
        List<Post> posts = postRepo.findPostsByPostType_Id(type);
        for(Post post: posts){
            PostDTO dto = new PostDTO();
            dto.setPostId(post.getPostId());
            dto.setPostType(post.getPostType().getId());
            dto.setStatus(post.getStatus());
            dto.setPostContent(post.getPostContent());
            dto.setUserId(post.getUser().getUserId());
            dto.setPostName(post.getPostName());
            dto.setPostDate(post.getPostDate());
            dto.setPostImage(post.getPostImage());
            dto.setTotalComment(postRepo.totalComment(dto.getPostId()));
            dto.setTotalLike(postRepo.totalLike(dto.getPostId()));
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping("/getAllType")
    public List<PostTypeDTO> getAllPostType(){
        List<PostType> list = postTypeRepo.findAll();
        List<PostTypeDTO> postTypes = new ArrayList<>();
        for(PostType entity : list){
            PostTypeDTO dto = new PostTypeDTO();
            dto.setId(entity.getId());
            dto.setTypeName(entity.getTypeName());
            postTypes.add(dto);
        }
        return postTypes;
    }

    @PostMapping("/like")
    public ResponseEntity<?> likePost(@RequestBody LikePostViewModel model){
        Post_Like likePost = likePostRepo.findByPost_PostIdAndUser_UserId(model.getPostId(), model.getUserId());
        if(likePost != null){
            likePostRepo.delete(likePost);
            return ResponseEntity.ok("Unliked");
        }
        else{
            Post_Like newLike = new Post_Like();
            User user = new User();
            user.setUserId(model.getUserId());
            Post post = new Post();
            post.setPostId(model.getPostId());
            newLike.setPost(post);
            newLike.setUser(user);
            likePostRepo.save(newLike);
            return ResponseEntity.ok("Liked");
        }
    }
    @GetMapping("/getPostByUserId/{id}")
    public List<PostDTO> getPostByUserId(@PathVariable("id") int userId){
        List<Post> posts = postRepo.getPostByUserID(userId);
        List<PostDTO> postDTOList = new ArrayList<>();
        if (posts != null){
            for (int i = 0; i <posts.size() ; i++) {
                PostDTO dto = new PostDTO();
                dto.setPostId(posts.get(i).getPostId());
                dto.setPostType(posts.get(i).getPostType().getId());
                dto.setStatus(posts.get(i).getStatus());
                dto.setPostContent(posts.get(i).getPostContent());
                dto.setUserId(posts.get(i).getUser().getUserId());
                dto.setPostName(posts.get(i).getPostName());
                dto.setPostDate(posts.get(i).getPostDate());
                dto.setPostImage(posts.get(i).getPostImage());
                postDTOList.add(dto);
            }
        }
        return postDTOList;
    }
    @GetMapping("/getPostByName/{name}")
    public List<PostDTO> getPostByName(@PathVariable("name") String name){
        List<Post> posts = postRepo.findByPostName(name);
        List<PostDTO> postDTOList = new ArrayList<>();
        if (posts != null){
            for (int i = 0; i <posts.size() ; i++) {
                PostDTO dto = new PostDTO();
                dto.setPostId(posts.get(i).getPostId());
                dto.setPostType(posts.get(i).getPostType().getId());
                dto.setStatus(posts.get(i).getStatus());
                dto.setPostContent(posts.get(i).getPostContent());
                dto.setUserId(posts.get(i).getUser().getUserId());
                dto.setPostName(posts.get(i).getPostName());
                dto.setPostDate(posts.get(i).getPostDate());
                dto.setPostImage(posts.get(i).getPostImage());
                postDTOList.add(dto);
            }
        }
        return postDTOList;
    }

    // Get all comments by post id
    @GetMapping("/{id}/comments")
    public List<PostCommentDTO> getCommentsByPostId(@PathVariable("id") int id){
        List<PostCommentDTO> dtos = new ArrayList<>();
        List<Post_Comment> comments = postCommentRepo.findByPost_PostId(id);
        for (Post_Comment item:comments){
            PostCommentDTO dto = new PostCommentDTO();
            dto.setId(item.getCommentId());
            dto.setPostId(item.getPost().getPostId());
            dto.setUserId(item.getUser().getUserId());
            dto.setContent(item.getCommentContent());
            dtos.add(dto);
        }
        return dtos;
    }

    // Get all post with status = 2
    @GetMapping("/getActivePost")
    public ResponseEntity<?> getActivePost(){
        List<Post> posts = postRepo.getActivePost();
        List<PostDTO> dtos = new ArrayList<>();
        for (Post entity : posts){
            PostDTO dto = new PostDTO();
            dto.setPostId(entity.getPostId());
            dto.setUserId(entity.getUser().getUserId());
            dto.setPostContent(entity.getPostContent());
            dto.setPostImage(entity.getPostImage());
            dto.setPostDate(entity.getPostDate());
            dto.setTotalLike(postRepo.totalLike(dto.getPostId()));
            dto.setTotalComment(postRepo.totalComment(dto.getPostId()));
            dto.setPostType(entity.getPostType().getId());
            dto.setPostName(entity.getPostName());
            dto.setStatus(entity.getStatus());
            dtos.add(dto);
        }
        return ResponseEntity.ok(dtos);
    }

    // Check post liked or not ( true = liked, false = unlike )
    @PostMapping("/checkLikeStatusPost")
    public ResponseEntity<?> checkLikeStatusPost(@RequestBody LikePostViewModel model) {
        Post_Like postLike = likePostRepo.findByPost_PostIdAndUser_UserId(model.getPostId(), model.getUserId());
        if (postLike != null) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    //Get all post with status = 1
    @GetMapping("/getPendingPost")
    public ResponseEntity<?> getUnactivePost(){
        List<Post> posts = postRepo.getUnactivePost();
        List<PostDTO> dtos = new ArrayList<>();
        for (Post entity : posts){
            PostDTO dto = new PostDTO();
            dto.setPostId(entity.getPostId());
            dto.setUserId(entity.getUser().getUserId());
            dto.setPostContent(entity.getPostContent());
            dto.setPostImage(entity.getPostImage());
            dto.setPostDate(entity.getPostDate());
            dto.setTotalLike(postRepo.totalLike(dto.getPostId()));
            dto.setTotalComment(postRepo.totalComment(dto.getPostId()));
            dto.setPostType(entity.getPostType().getId());
            dto.setPostName(entity.getPostName());
            dto.setStatus(entity.getStatus());
            dtos.add(dto);
        }
        return ResponseEntity.ok(dtos);
    }
}
