package com.hectal.job.service;

import com.hectal.job.payload.AuthResponse;
import com.hectal.job.payload.LoginRequest;
import com.hectal.job.payload.SignupRequest;
import org.springframework.stereotype.Service;


public interface AuthService {

    AuthResponse signUp(SignupRequest req) throws Exception;

    AuthResponse login(LoginRequest req) throws Exception;
}
