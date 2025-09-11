package com.jobportal.job_portal.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {
    private Long id;
    private String bio;
    private String skills;
    private String experience;
    private String resumeUrl;
    private Long userId;
    private String username;  // optional, for employers to see
}
