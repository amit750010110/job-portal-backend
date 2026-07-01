package com.hectal.job.service.impl;

import com.hectal.job.domain.UserRole;
import com.hectal.job.domain.UserStatus;
import com.hectal.job.mapper.UserMapper;
import com.hectal.job.modal.User;
import com.hectal.job.payload.AuthResponse;
import com.hectal.job.payload.LoginRequest;
import com.hectal.job.payload.SignupRequest;
import com.hectal.job.repository.UserRepository;
import com.hectal.job.security.CustomUserDetailsService;
import com.hectal.job.security.JwtProvider;
import com.hectal.job.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public AuthResponse signUp(SignupRequest req) throws Exception {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new Exception("User is already registered" + req.getEmail());
        }

        if (req.getRole() == UserRole.ROLE_ADMIN) {
            throw new Exception("Can not self register as a ADMIN");
        }

        User user = User.builder().
                fullName(req.getFullName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .role(req.getRole())
                .status(UserStatus.ACTIVE)
                .password(passwordEncoder.encode(req.getPassword()))
                .lastLogin(LocalDateTime.now())
                .build();
        User saveUser = userRepository.save(user);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword(), authorities
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication, saveUser.getId());

        AuthResponse res = new AuthResponse();
        res.setTitle("Welcome " + saveUser.getFullName());
        res.setMessage("Registered Successfully");
        res.setJwt(jwt);
        res.setUser(UserMapper.toDTO(saveUser));
        return res;
    }

    @Override
    public AuthResponse login(LoginRequest req) throws Exception {
        Authentication authentication = authenticate(req.getEmail(), req.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(req.getEmail());
        String jwt = jwtProvider.generateToken(authentication, user.getId());

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse res = new AuthResponse();
        res.setTitle("Welcome " + user.getFullName());
        res.setMessage("Login Successfully");
        res.setJwt(jwt);
        res.setUser(UserMapper.toDTO(user));


        return res;
    }

    private Authentication authenticate(String email, String password) throws Exception {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        if (userDetails == null) {
            throw new Exception("user not found with email" + email);

        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new Exception("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
