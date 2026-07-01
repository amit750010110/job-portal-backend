package com.hectal.job.controller;

import com.hectal.job.domain.UserRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping
    public String HomeController(){
        return "<h1>Job Portal User System</h1>" + UserRole.ROLE_JOB_SEEKER;
    }
}
