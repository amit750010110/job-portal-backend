package com.hectal.job.controller;

import com.hectal.job.dto.response.UserResponse;
import com.hectal.job.mapper.UserMapper;
import com.hectal.job.payload.UpdateUserRequest;
import com.hectal.job.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<UserResponse> getProfile(@RequestHeader("X-User-Email") String email) throws Exception {
        return ResponseEntity.ok(UserMapper.toDTO(userService.getUserByEmail(email)));

    }

    @PutMapping("/api/users/profile")
    public ResponseEntity<UserResponse> updateProfile(@RequestHeader("X-User-Email") String email, @RequestBody UpdateUserRequest req) throws Exception {
        return ResponseEntity.ok(userService.updateProfile(email, req));

    }

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<UserResponse> getuserById(@PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(UserMapper.toDTO(userService.getUserById(userId)));

    }

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(UserMapper.toDTOList(userService.getAllUser()));
    }

    @PatchMapping("api/users/{userId}/suspend")
    public ResponseEntity<UserResponse> suspendUser(@PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(userService.suppendUser(userId));
    }

    @PatchMapping("api/users/{userId}/activate")
    public ResponseEntity<UserResponse> activateUser(@PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(userService.activateUser(userId));
    }

    @DeleteMapping("/api/users/{userId}/delete")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long userId) throws Exception {
        UserResponse userResponse = userService.deleteUser(userId);
        return ResponseEntity.ok(userResponse);
    }


}
