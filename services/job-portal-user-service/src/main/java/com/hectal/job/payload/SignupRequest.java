package com.hectal.job.payload;


import com.hectal.job.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank(message = "Full Name is mandatory")
    private String fullName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email name is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    //    @NotBlank(message = "Phone number is mandatory")
    private String phone;

    @NotNull(message = "Role is mandatory")
    private UserRole role;
}
