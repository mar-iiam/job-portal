package com.jobportal.job_portal.controller;

import com.jobportal.job_portal.dto.JobRequest;
import com.jobportal.job_portal.dto.JobResponse;
import com.jobportal.job_portal.dto.JobSearchRequest;
import com.jobportal.job_portal.services.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // ✅ Only authenticated users can create jobs
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody JobRequest request) {
        return ResponseEntity.ok(jobService.createJob(request));
    }

    // ✅ Anyone authenticated can view all jobs
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    // ✅ Get only the jobs posted by the currently logged-in user
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<JobResponse>> getMyJobs() {
        return ResponseEntity.ok(jobService.getJobsByLoggedInUser());
    }
    @PostMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<JobResponse>> searchJobs(@RequestBody JobSearchRequest request) {
        return ResponseEntity.ok(jobService.searchJobs(request));
    }


}
