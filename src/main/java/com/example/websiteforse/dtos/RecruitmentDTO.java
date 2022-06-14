package com.example.websiteforse.dtos;

import java.util.Date;

public class RecruitmentDTO {
    private int recruitmentId;
    private int companyId;
    private String recruitmentName;
    private Date dateCreated;
    private String recruitmentContent;
    private String recruitmentImage;

    public RecruitmentDTO(){}

    public int getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(int recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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

    public String getRecruitmentName() {
        return recruitmentName;
    }

    public void setRecruitmentName(String recruitmentName) {
        this.recruitmentName = recruitmentName;
    }
}
