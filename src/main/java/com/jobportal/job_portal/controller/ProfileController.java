package com.jobportal.job_portal.controller;


import com.jobportal.job_portal.dto.ProfileRequest;
import com.jobportal.job_portal.dto.ProfileResponse;
import com.jobportal.job_portal.services.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // ✅ View my profile
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileResponse> getMyProfile() {
        return ResponseEntity.ok(profileService.getMyProfile());
    }

    // ✅ Update my profile
    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileResponse> updateMyProfile(@Valid @RequestBody ProfileRequest request) {
        return ResponseEntity.ok(profileService.updateMyProfile(request));
    }

    // ✅ Employers can view applicant’s profile by userId
    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileResponse> getProfileByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getProfileByUserId(userId));
    }
}
