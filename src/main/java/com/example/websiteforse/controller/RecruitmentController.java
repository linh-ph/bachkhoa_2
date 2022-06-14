package com.example.websiteforse.controller;

import com.example.websiteforse.dtos.JobApplicationDTO;
import com.example.websiteforse.dtos.RecruitmentDTO;
import com.example.websiteforse.entity.Company;
import com.example.websiteforse.entity.Job_Application;
import com.example.websiteforse.entity.Recruitment;
import com.example.websiteforse.repository.CompanyRepository;
import com.example.websiteforse.repository.JobApplicationRepository;
import com.example.websiteforse.repository.RecruitmentRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/recruitment")
public class RecruitmentController {
    @Autowired
    private RecruitmentRepository recruitmentRepo;

    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private JobApplicationRepository jobAppRepo;

    // Get all Recruitment
    @GetMapping("/getall")
    public ResponseEntity<?> getAllRecruitment(){
        List<Recruitment> listEntity = recruitmentRepo.findAll();
        List<RecruitmentDTO> listdtos = new ArrayList<>();
        for (Recruitment entity : listEntity){
            RecruitmentDTO dto = new RecruitmentDTO();
            dto.setRecruitmentName(entity.getRecruitmentName());
            dto.setRecruitmentId(entity.getRecruitmentId());
            dto.setRecruitmentContent(entity.getRecruitmentContent());
            dto.setRecruitmentImage(entity.getRecruitmentImage());
            dto.setDateCreated(entity.getDateCreated());
            dto.setCompanyId(entity.getCompany().getCompanyId());
            listdtos.add(dto);
        }
        return ResponseEntity.ok(listdtos);
    }

    // Add Recruitment
    @PostMapping("/add")
    public ResponseEntity<?> addRecruitment(@RequestBody RecruitmentDTO request){
        Recruitment recruitment = new Recruitment();
        Company company = new Company();
        company.setCompanyId(request.getCompanyId());
        recruitment.setRecruitmentName(request.getRecruitmentName());
        recruitment.setRecruitmentContent(request.getRecruitmentContent());
        recruitment.setRecruitmentImage(request.getRecruitmentImage());
        Date date = new Date(new java.util.Date().getTime());
        recruitment.setDateCreated(date);
        recruitment.setCompany(company);
        recruitmentRepo.save(recruitment);
        return ResponseEntity.ok("Add Recruitment success");
    }

    // Delete Recruitment by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecruitment(@PathVariable("id") int id){
        recruitmentRepo.deleteById(id);
        return ResponseEntity.ok("Delete Recruitment success");
    }

    // Edit Recruitment
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editRecruitment(@RequestBody RecruitmentDTO dto, @PathVariable("id") int id){
        Recruitment recruitment = recruitmentRepo.getById(id);
        if(recruitment != null){
            recruitment.setRecruitmentName(dto.getRecruitmentName());
            recruitment.setRecruitmentContent(dto.getRecruitmentContent());
            recruitment.setRecruitmentImage(dto.getRecruitmentImage());
            recruitmentRepo.save(recruitment);
            return ResponseEntity.ok("Edit Recruitment success");
        }
        else{
            return ResponseEntity.badRequest().body("Not found recruitment id");
        }
    }

    // Get Recruitment by Id
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getRecruitmentById(@PathVariable("id") int id){
        Recruitment recruitment = recruitmentRepo.findRecruitmentByRecruitmentId(id);
        RecruitmentDTO dto = new RecruitmentDTO();
        if(recruitment != null){
            dto.setRecruitmentName(recruitment.getRecruitmentName());
            dto.setRecruitmentId(recruitment.getRecruitmentId());
            dto.setRecruitmentContent(recruitment.getRecruitmentContent());
            dto.setRecruitmentImage(recruitment.getRecruitmentImage());
            dto.setDateCreated(recruitment.getDateCreated());
            dto.setCompanyId(recruitment.getCompany().getCompanyId());
            return ResponseEntity.ok(dto);
        }
        else{
            return ResponseEntity.badRequest().body("Not found recruitment id");
        }
    }

    // Get Recruitment by Recruitment Name
    @GetMapping("/getByName/{name}")
    public ResponseEntity<?> getRecruitmentByName(@RequestParam(value = "name", required = false) String name){
        List<Recruitment> recruitments = recruitmentRepo.findByRecruitmentName(name);
        List<RecruitmentDTO> listdtos = new ArrayList<>();
        if(recruitments != null){
            for(Recruitment entity : recruitments){
                RecruitmentDTO dto = new RecruitmentDTO();
                dto.setRecruitmentName(entity.getRecruitmentName());
                dto.setRecruitmentId(entity.getRecruitmentId());
                dto.setRecruitmentContent(entity.getRecruitmentContent());
                dto.setRecruitmentImage(entity.getRecruitmentImage());
                dto.setDateCreated(entity.getDateCreated());
                dto.setCompanyId(entity.getCompany().getCompanyId());
                listdtos.add(dto);
            }
        }
        return ResponseEntity.ok(listdtos);
    }

    // Get Recruitment By Company id
    @GetMapping("/getByCompanyId/{id}")
    public ResponseEntity<?> getRecruitmentByCompanyId(@PathVariable("id") int id){
        List<Recruitment> recruitments = recruitmentRepo.findRecruitmentByCompany_CompanyId(id);
        List<RecruitmentDTO> listdtos = new ArrayList<>();
        if(recruitments != null){
            for(Recruitment entity : recruitments){
                RecruitmentDTO dto = new RecruitmentDTO();
                dto.setRecruitmentName(entity.getRecruitmentName());
                dto.setRecruitmentId(entity.getRecruitmentId());
                dto.setRecruitmentContent(entity.getRecruitmentContent());
                dto.setRecruitmentImage(entity.getRecruitmentImage());
                dto.setDateCreated(entity.getDateCreated());
                dto.setCompanyId(entity.getCompany().getCompanyId());
                listdtos.add(dto);
            }
        }
        else{
            return ResponseEntity.badRequest().body("Not found company id");
        }
        return ResponseEntity.ok(listdtos);
    }

    @GetMapping("/getApplyJobByRecruitmentId/{recruitid}")
    public ResponseEntity<?> getApplyJobByRecruitmentId(@PathVariable("recruitid") int recruitId){
        List<Job_Application> listJob = jobAppRepo.findByRecruitment_RecruitmentId(recruitId);
        List<JobApplicationDTO> listDto = new ArrayList<>();
        for (Job_Application entity : listJob){
            JobApplicationDTO dto = new JobApplicationDTO();
            dto.setEmail(entity.getEmail());
            dto.setRecruitmentId(entity.getRecruitment().getRecruitmentId());
            dto.setUserId(entity.getUser().getUserId());
            dto.setId(entity.getId());
            dto.setPhoneNumber(entity.getPhoneNumber());
            dto.setCvUrl(entity.getCv_url());
            listDto.add(dto);
        }
        return ResponseEntity.ok(listDto);
    }
}
