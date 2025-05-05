package com.iotproject.iotproject.Service;

import com.iotproject.iotproject.Dto.PasswordUpdateDto;
import com.iotproject.iotproject.Dto.ResponseDto;
import com.iotproject.iotproject.Dto.UserProfileDto;
import com.iotproject.iotproject.Entity.User;
import com.iotproject.iotproject.Repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.iotproject.iotproject.Service.OtpService;

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
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileDto dto = new UserProfileDto();
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public ResponseDto updatePassword(@Valid PasswordUpdateDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

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
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

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