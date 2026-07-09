package com.hectal.job.mapper;

import com.hectal.job.dto.response.UserResponse;
import com.hectal.job.modal.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponse toDTO(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setFullName(user.getFullName());
        dto.setProfileImage(user.getProfileImage());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setLastLogin(user.getLastLogin());

        return dto;

    }

    public static List<UserResponse> toDTOList(List<User> users) {

        return users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }
}
