package com.jobportal.job_portal.dto;


import lombok.Data;

@Data
public class JobRequest {
    private String title;
    private String description;
    private String location;
    private String type;
    private String companyName;
}
