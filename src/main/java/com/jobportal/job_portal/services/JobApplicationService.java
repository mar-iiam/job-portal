package com.jobportal.job_portal.services;


import com.jobportal.job_portal.models.*;
import com.jobportal.job_portal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Apply to a job
// ✅ Apply to a job
    public JobApplication applyToJob(Long jobId) {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // ✅ Query DB BEFORE creating new application
        if (jobApplicationRepository.existsByApplicantIdAndJobId(user.getId(), jobId)) {
            throw new IllegalStateException("You have already applied for this job");
        }

        JobApplication application = JobApplication.builder()
                .job(job)
                .applicant(user)
                .build();

        return jobApplicationRepository.saveAndFlush(application); // force flush immediately
    }




    // ✅ View applicants for a job (only if logged-in user posted it)
    public List<JobApplication> getApplicantsForJob(Long jobId) {
        String username = getCurrentUsername();
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getPostedBy().getUsername().equals(username)) {
            throw new RuntimeException("Not authorized to view applicants for this job");
        }

        return jobApplicationRepository.findByJobId(jobId);
    }

    // ✅ Get jobs the current user applied to
    public List<JobApplication> getMyApplications() {
        String username = getCurrentUsername();
        return jobApplicationRepository.findByApplicantUsername(username);
    }

    private String getCurrentUsername() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
