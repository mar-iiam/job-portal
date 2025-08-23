package com.jobportal.job_portal.services;

import com.jobportal.job_portal.dto.JobRequest;
import com.jobportal.job_portal.dto.JobResponse;
import com.jobportal.job_portal.dto.JobSearchRequest;
import com.jobportal.job_portal.models.Job;
import com.jobportal.job_portal.models.User;
import com.jobportal.job_portal.repository.JobRepository;
import com.jobportal.job_portal.repository.UserRepository;
import com.jobportal.job_portal.specification.JobSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Create Job
    public JobResponse createJob(JobRequest request) {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .type(request.getType())
                .companyName(request.getCompanyName())
                .salary(request.getSalary()) // ✅ make sure JobRequest has salary
                .postedDate(new Date())
                .postedBy(user)
                .build();

        Job saved = jobRepository.save(job);
        return mapToResponse(saved);
    }

    // ✅ Get All Jobs
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ Get Jobs of Logged-in User
    public List<JobResponse> getJobsByLoggedInUser() {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jobRepository.findByPostedBy(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<JobResponse> searchJobs(JobSearchRequest request) {
        Specification<Job> spec = Specification.allOf(
                JobSpecifications.hasKeyword(request.getKeyword()),
                JobSpecifications.hasLocation(request.getLocation()),
                JobSpecifications.hasType(request.getType()),
                JobSpecifications.hasMinSalary(request.getMinSalary()),
                JobSpecifications.hasMaxSalary(request.getMaxSalary())
        );


        return jobRepository.findAll(spec)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    // ✅ Utility: Get Current Username
    private String getCurrentUsername() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No authenticated user");
        }
        return auth.getName();
    }

    // ✅ Mapper
    private JobResponse mapToResponse(Job job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .location(job.getLocation())
                .type(job.getType())
                .companyName(job.getCompanyName())
                .salary(job.getSalary())
                .postedDate(job.getPostedDate())
                .postedBy(job.getPostedBy().getUsername())
                .build();
    }
}
