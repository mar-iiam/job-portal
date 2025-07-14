package com.jobportal.job_portal.controller;

import com.jobportal.job_portal.dto.RegisterRequest;
import com.jobportal.job_portal.dto.RegisterResponse;
import com.jobportal.job_portal.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }
}
