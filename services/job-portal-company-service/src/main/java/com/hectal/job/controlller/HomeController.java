package com.hectal.job.controlller;

import com.hectal.job.domain.UserRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/hello")
    public String helloController() {
        return "This is company service " + UserRole.ROLE_ADMIN;
    }
}
