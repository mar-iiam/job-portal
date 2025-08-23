package com.jobportal.job_portal.repository;

import com.jobportal.job_portal.models.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByJobId(Long jobId);

    List<JobApplication> findByApplicantUsername(String username);

    // ✅ Add this line
    boolean existsByApplicantIdAndJobId(Long applicantId, Long jobId);
}
