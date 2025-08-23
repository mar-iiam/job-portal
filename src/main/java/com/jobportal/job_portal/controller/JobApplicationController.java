package com.jobportal.job_portal.controller;

import com.jobportal.job_portal.models.JobApplication;
import com.jobportal.job_portal.services.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    // ✅ Apply to a job
    @PostMapping("/apply/{jobId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<JobApplication> applyToJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobApplicationService.applyToJob(jobId));
    }

    // ✅ View applicants for a job (only employer who posted it)
    @GetMapping("/job/{jobId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<JobApplication>> getApplicantsForJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobApplicationService.getApplicantsForJob(jobId));
    }

    // ✅ View jobs the current user applied to
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<JobApplication>> getMyApplications() {
        return ResponseEntity.ok(jobApplicationService.getMyApplications());
    }
}
