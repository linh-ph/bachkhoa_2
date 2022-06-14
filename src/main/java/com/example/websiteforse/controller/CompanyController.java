package com.example.websiteforse.controller;

import com.example.websiteforse.dtos.CompanyDTO;
import com.example.websiteforse.entity.Company;
import com.example.websiteforse.entity.User;
import com.example.websiteforse.repository.CompanyRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/conpany")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepo;

    @GetMapping("/getall")
    public ResponseEntity<?> getAllCompany() {
        List<Company> listEntity = companyRepo.findAll();
        List<CompanyDTO> listdtos = new ArrayList<>();
        for(Company entity : listEntity){
            CompanyDTO dto = new CompanyDTO();
            dto.setCompanyId(entity.getCompanyId());
            dto.setCompanyName(entity.getCompanyName());
            dto.setUserId(entity.getUser().getUserId());
            listdtos.add(dto);
        }
        return ResponseEntity.ok(listdtos);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCompany(@RequestBody CompanyDTO request){
        Company company = new Company();
        User user = new User();
        company.setCompanyName(request.getCompanyName());
        user.setUserId(request.getUserId());
        if(companyRepo.findByUser_UserId(request.getUserId()) != null){
            return ResponseEntity.ok("This user already have company");
        }
        company.setUser(user);
        companyRepo.save(company);
        return ResponseEntity.ok("Add company success");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable("id") int id){
        companyRepo.deleteById(id);
        return ResponseEntity.ok("Delete company success");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editCompany(@RequestBody CompanyDTO dto, @PathVariable("id") int id){
        Company company = companyRepo.findByCompanyId(id);
        if(company != null){
            company.setCompanyName(dto.getCompanyName());
            companyRepo.save(company);
            return ResponseEntity.ok("Edit company success");
        }
        else{
            return ResponseEntity.badRequest().body("Not found company id");
        }
    }
}
