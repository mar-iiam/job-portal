package com.jobportal.job_portal.repository;


import com.jobportal.job_portal.models.Job;
import com.jobportal.job_portal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> , JpaSpecificationExecutor<Job> {
    List<Job> findByPostedBy(User user);
}
