package com.jobportal.job_portal.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequest {
    private String bio;
    private String skills;
    private String experience;
    private String resumeUrl;
}
