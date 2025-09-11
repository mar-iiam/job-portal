package com.jobportal.job_portal.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One-to-one with User
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String bio;
    private String skills;       // e.g., "Java, Spring Boot, SQL"
    private String experience;   // short description or years
    private String resumeUrl;    // link to uploaded resume (S3, local storage, etc.)
}
