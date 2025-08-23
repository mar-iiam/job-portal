package com.jobportal.job_portal.dto;

import lombok.Data;

@Data
public class JobSearchRequest {
    private String keyword;
    private String location;
    private String type;
    private Double minSalary;
    private Double maxSalary;
}
