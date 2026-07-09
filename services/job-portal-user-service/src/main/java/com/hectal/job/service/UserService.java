package com.hectal.job.service;

import com.hectal.job.dto.response.UserResponse;
import com.hectal.job.modal.User;
import com.hectal.job.payload.UpdateUserRequest;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email) throws Exception;

    User getUserById(Long id) throws Exception;



    List<User> getAllUser();

    UserResponse updateProfile(String email, UpdateUserRequest userRequest);

    // Admin Action

    UserResponse suppendUser(Long id) throws Exception;

    UserResponse deleteUser(Long id) throws Exception;// Soft Delete

    UserResponse activateUser(Long id) throws Exception;
}
