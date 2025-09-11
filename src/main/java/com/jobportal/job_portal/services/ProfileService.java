package com.jobportal.job_portal.services;


import com.jobportal.job_portal.dto.ProfileRequest;
import com.jobportal.job_portal.dto.ProfileResponse;
import com.jobportal.job_portal.models.Profile;
import com.jobportal.job_portal.models.User;
import com.jobportal.job_portal.repository.ProfileRepository;
import com.jobportal.job_portal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService  {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    public ProfileResponse getMyProfile() {
        User currentUser = getCurrentUser();
        Profile profile = profileRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return toResponse(profile);
    }


    public ProfileResponse updateMyProfile(ProfileRequest request) {
        User currentUser = getCurrentUser();

        Profile profile = profileRepository.findByUser(currentUser)
                .orElse(Profile.builder().user(currentUser).build());

        profile.setBio(request.getBio());
        profile.setSkills(request.getSkills());
        profile.setExperience(request.getExperience());
        profile.setResumeUrl(request.getResumeUrl());

        Profile saved = profileRepository.save(profile);
        return toResponse(saved);
    }


    public ProfileResponse getProfileByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return toResponse(profile);
    }

    private ProfileResponse toResponse(Profile profile) {
        return ProfileResponse.builder()
                .id(profile.getId())
                .bio(profile.getBio())
                .skills(profile.getSkills())
                .experience(profile.getExperience())
                .resumeUrl(profile.getResumeUrl())
                .userId(profile.getUser().getId())
                .username(profile.getUser().getUsername())
                .build();
    }
}
