package com.jobportal.job_portal.repository;


import com.jobportal.job_portal.models.Profile;
import com.jobportal.job_portal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);
}
