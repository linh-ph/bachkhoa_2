package com.example.websiteforse.controller;

import com.example.websiteforse.dtos.DiscussionCommentDTO;
import com.example.websiteforse.dtos.DiscussionDTO;
import com.example.websiteforse.dtos.PostCommentDTO;
import com.example.websiteforse.dtos.PostDTO;
import com.example.websiteforse.entity.*;
import com.example.websiteforse.repository.DiscussionCommentRepository;
import com.example.websiteforse.repository.DiscussionRepository;
import com.example.websiteforse.service.DiscussionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/discussion")
public class DiscussionController {
    @Autowired
    private DiscussionRepository discussionRepo;

    @Autowired
    private DiscussionServiceImpl discussionService;

    @Autowired
    private DiscussionCommentRepository discussionCommentRepo;

    @GetMapping ("/getall")
    public List <DiscussionDTO> showAllDiscus(){
        List<Discussion> discussions = discussionRepo.findAll();
        List<DiscussionDTO> dtos = new ArrayList<>();
        for (int i = 0; i < discussions.size(); i++) {
            DiscussionDTO dto = new DiscussionDTO();
            dto.setDiscussionId(discussions.get(i).getDiscussionId());
            dto.setDiscussionContent(discussions.get(i).getDiscussionContent());
            dto.setDiscussionTitle(discussions.get(i).getDiscussionTitle());
            dto.setUserId(discussions.get(i).getUser().getUserId());
            dto.setSubjectId(discussions.get(i).getSubject().getSubjectId());
            dto.setDiscussDate(discussions.get(i).getDiscussDate());
            dto.setStatus(discussions.get(i).getStatus());
            dtos.add(dto);
        }
        return dtos;
    }
     @PostMapping("/add")
    public ResponseEntity<?> addDiscuss (@RequestBody DiscussionDTO request){
        Discussion discussion = new Discussion();
        User user = new User();
        user.setUserId(request.getUserId());
        Subject subject = new Subject();
        subject.setSubjectId(request.getSubjectId());
        discussion.setDiscussionTitle(request.getDiscussionTitle());
        discussion.setDiscussionContent(request.getDiscussionContent());
        discussion.setSubject(subject);
        discussion.setUser(user);
        discussion.setStatus(1);
         Date date = new Date( new java.util.Date().getTime());
        discussion.setDiscussDate(date);
        discussionService.save(discussion);
        return ResponseEntity.ok("Add success");
     }

     @DeleteMapping ("/delete/{id}")
    public ResponseEntity <?> deleteDiscuss(@PathVariable ("id") int id){
        List<Discussion_Comment> comments = discussionCommentRepo.findByDiscussion_DiscussionId(id);
        for(Discussion_Comment comment : comments){
            discussionCommentRepo.deleteById(comment.getCommentId());
        }
        discussionRepo.deleteById(id);
        return ResponseEntity.ok("Delete success");
     }

     @PutMapping ("/edit/{id}")
    public ResponseEntity<?> updateDisscuss (@RequestBody DiscussionDTO dto, @PathVariable("id") int id)
     {
         Discussion discuss = discussionRepo.findByDiscussionId(id);
         if(discuss != null)
         {
             discuss.setDiscussionTitle(dto.getDiscussionTitle());
             discuss.setDiscussionContent(dto.getDiscussionContent());
             discussionService.save(discuss);
             return ResponseEntity.ok("Update Discussion Success !!");

         }
         else{
             return ResponseEntity.badRequest().body("Not found discuss id");
         }
     }
    @GetMapping("/getDiscussByName/{name}")
    public List<DiscussionDTO> getDiscussByName(@PathVariable("name") String name)
    {
        List<Discussion> discussions = discussionRepo.findDiscussionByDiscussionTitle(name);
        List<DiscussionDTO> discussionDTOList = new ArrayList<>();
        if (discussions != null){
            for (int i = 0; i < discussions.size(); i++) {
                DiscussionDTO dto = new DiscussionDTO();
                dto.setDiscussionId(discussions.get(i).getDiscussionId());
                dto.setDiscussionTitle(discussions.get(i).getDiscussionTitle());
                dto.setDiscussionContent(discussions.get(i).getDiscussionContent());
                dto.setDiscussDate(discussions.get(i).getDiscussDate());
                dto.setUserId(discussions.get(i).getUser().getUserId());
                dto.setSubjectId(discussions.get(i).getSubject().getSubjectId());
                dto.setStatus(discussions.get(i).getStatus());
                discussionDTOList.add(dto);
            }
        }
        return discussionDTOList;
    }
    @GetMapping("/getDiscussBySubjectID/{id}")
    public List<DiscussionDTO> getDiscussBySubjectID(@PathVariable("id") int id)
    {
        List<Discussion> discussions = discussionRepo.findDiscussionBySubjectId(id);
        List<DiscussionDTO> discussionDTOList = new ArrayList<>();
        if (discussions != null){
            for (int i = 0; i < discussions.size(); i++) {
                DiscussionDTO dto = new DiscussionDTO();
                dto.setDiscussionId(discussions.get(i).getDiscussionId());
                dto.setDiscussionTitle(discussions.get(i).getDiscussionTitle());
                dto.setDiscussionContent(discussions.get(i).getDiscussionContent());
                dto.setDiscussDate(discussions.get(i).getDiscussDate());
                dto.setUserId(discussions.get(i).getUser().getUserId());
                dto.setSubjectId(discussions.get(i).getSubject().getSubjectId());
                dto.setStatus(discussions.get(i).getStatus());
                discussionDTOList.add(dto);
            }
        }
        return discussionDTOList;
    }

    // Get all comments by post id
    @GetMapping("/{id}/comments")
    public List<DiscussionCommentDTO> getCommentsByDiscussionId(@PathVariable("id") int id) {
        List<DiscussionCommentDTO> dtos = new ArrayList<>();
        List<Discussion_Comment> comments = discussionCommentRepo.findByDiscussion_DiscussionId(id);
        for (Discussion_Comment item : comments) {
            DiscussionCommentDTO dto = new DiscussionCommentDTO();
            dto.setId(item.getCommentId());
            dto.setDiscussionId(item.getDiscussion().getDiscussionId());
            dto.setUserId(item.getUser().getUserId());
            dto.setContent(item.getCommentContent());
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping("/searchDiscussInSubjectByTitle/{id}")
    public List<DiscussionDTO>searchDiscussInSubjectByTitle(@PathVariable("id") int id, @RequestParam String title){
    List<Discussion> discussions = discussionRepo.searchDiscussInSubjectByTitle(id,title);

        List<DiscussionDTO> discussionDTOList = new ArrayList<>();
        if (discussions != null){
            for (int i = 0; i < discussions.size(); i++) {
                DiscussionDTO dto = new DiscussionDTO();
                dto.setDiscussionId(discussions.get(i).getDiscussionId());
                dto.setDiscussionTitle(discussions.get(i).getDiscussionTitle());
                dto.setDiscussionContent(discussions.get(i).getDiscussionContent());
                dto.setDiscussDate(discussions.get(i).getDiscussDate());
                dto.setUserId(discussions.get(i).getUser().getUserId());
                dto.setSubjectId(discussions.get(i).getSubject().getSubjectId());
                dto.setStatus(discussions.get(i).getStatus());
                discussionDTOList.add(dto);
            }
        }
        return discussionDTOList;
    }

    @GetMapping("/getDiscussByID/{id}")
    public DiscussionDTO getDiscussByID(@PathVariable("id") int id)
    {
        Discussion discussions = discussionRepo.findByDiscussionId(id);
        DiscussionDTO dto = new DiscussionDTO();
        if (discussions != null){
                dto.setDiscussionId(discussions.getDiscussionId());
                dto.setDiscussionTitle(discussions.getDiscussionTitle());
                dto.setDiscussionContent(discussions.getDiscussionContent());
                dto.setDiscussDate(discussions.getDiscussDate());
                dto.setUserId(discussions.getUser().getUserId());
                dto.setSubjectId(discussions.getSubject().getSubjectId());
                dto.setStatus(discussions.getStatus());
        }
        return dto;
    }
    @GetMapping("/getDiscussByuserID/{id}")
    public List<DiscussionDTO> getDiscussByUserId(@PathVariable("id") int id) {
        List<Discussion> discussions = discussionRepo.searchDiscussByUseID(id);
        List<DiscussionDTO> discussionDTOList = new ArrayList<>();
        if (discussions != null){
            for (int i = 0; i < discussions.size(); i++) {
                DiscussionDTO dto = new DiscussionDTO();
                dto.setDiscussionId(discussions.get(i).getDiscussionId());
                dto.setDiscussionTitle(discussions.get(i).getDiscussionTitle());
                dto.setDiscussionContent(discussions.get(i).getDiscussionContent());
                dto.setDiscussDate(discussions.get(i).getDiscussDate());
                dto.setUserId(discussions.get(i).getUser().getUserId());
                dto.setSubjectId(discussions.get(i).getSubject().getSubjectId());
                dto.setStatus(discussions.get(i).getStatus());
                discussionDTOList.add(dto);
            }
        }
        return discussionDTOList;
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveDiscussion(@PathVariable("id") int id, @RequestBody DiscussionDTO dto){
        Discussion discussion = discussionRepo.findByDiscussionId(id);
        if(discussion != null){
            discussion.setStatus(dto.getStatus());
            discussionRepo.save(discussion);
            return ResponseEntity.ok("Status discussion change success");
        }
        else{
            return ResponseEntity.badRequest().body("Not found discussion id" +id);
        }
    }
}
