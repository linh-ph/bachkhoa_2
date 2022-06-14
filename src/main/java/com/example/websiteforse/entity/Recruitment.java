package com.example.websiteforse.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Recruitment")
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private int recruitmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "recruitment_name")
    private String recruitmentName;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "recruitment_content")
    private String recruitmentContent;

    @Column(name = "recruitment_image")
    private String recruitmentImage;

    public Recruitment(){}


    public int getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(int recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getRecruitmentContent() {
        return recruitmentContent;
    }

    public void setRecruitmentContent(String recruitmentContent) {
        this.recruitmentContent = recruitmentContent;
    }

    public String getRecruitmentImage() {
        return recruitmentImage;
    }

    public void setRecruitmentImage(String recruitmentImage) {
        this.recruitmentImage = recruitmentImage;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getRecruitmentName() {
        return recruitmentName;
    }

    public void setRecruitmentName(String recruitmentName) {
        this.recruitmentName = recruitmentName;
    }
}
