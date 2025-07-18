package com.iotproject.iotproject.service;

import com.iotproject.iotproject.constants.AuthMessages;
import com.iotproject.iotproject.dto.PasswordUpdateDto;
import com.iotproject.iotproject.dto.ResponseDto;
import com.iotproject.iotproject.dto.UserProfileDto;
import com.iotproject.iotproject.entity.User;
import com.iotproject.iotproject.repo.UserRepository;
import com.iotproject.iotproject.service.OtpService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;
    private OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserProfileDto getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println(email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(AuthMessages.NOT_FOUND));

        UserProfileDto dto = new UserProfileDto();
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public ResponseDto updatePassword(@Valid PasswordUpdateDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(AuthMessages.NOT_FOUND));

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }
        if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())){
            throw new RuntimeException("Old password matches new password");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
        return ResponseDto.builder()
                .success(true)
                .message("Password updated successfully")
                .build();
    }

    public ResponseDto resetPasswordWithOtp(String username, String otp, String newPassword) {
        // Step 1: Find the user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(AuthMessages.NOT_FOUND));

        // Step 2: Verify OTP
        if (!otpService.verifyOtp(user.getEmail(), otp)) {
            return new ResponseDto(false, null, "Invalid or expired OTP");
        }

        // Step 3: Update password
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);

        // Step 4: Clear OTP after successful reset
        otpService.clearOtp(user.getEmail());

        return new ResponseDto(true, null, "Password reset successfully");
    }
}