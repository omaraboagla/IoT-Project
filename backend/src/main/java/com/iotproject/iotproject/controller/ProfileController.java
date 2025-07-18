package com.iotproject.iotproject.controller;

import com.iotproject.iotproject.constants.ApiPaths;
import com.iotproject.iotproject.dto.ForgotPasswordRequestDto;
import com.iotproject.iotproject.dto.PasswordUpdateDto;
import com.iotproject.iotproject.dto.ResetPasswordRequestDto;
import com.iotproject.iotproject.dto.ResponseDto;
import com.iotproject.iotproject.dto.UserProfileDto;
import com.iotproject.iotproject.service.OtpService;
import com.iotproject.iotproject.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(ApiPaths.PROFILE_BASE)
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private OtpService otpService;


    @GetMapping
    public ResponseEntity<UserProfileDto> getProfile() {
        return ResponseEntity.ok(profileService.getCurrentUserProfile());
    }

    @PutMapping(ApiPaths.UPDATE_PASSWORD)
    public ResponseEntity<ResponseDto> updatePassword(@RequestBody PasswordUpdateDto dto) {
       ResponseDto response =  profileService.updatePassword(dto);
        return ResponseEntity.ok(response);
    }
    @PostMapping(ApiPaths.FORGET_PASSWORD)
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequestDto request) {
        String email = request.getEmail();
        otpService.sendOtp(email);
        return ResponseEntity.ok("OTP sent to your email.");
    }

    @PostMapping(ApiPaths.RESET_PASSWORD)
    public ResponseEntity<ResponseDto> resetPassword(@RequestBody ResetPasswordRequestDto dto) {
        ResponseDto response = profileService.resetPasswordWithOtp(
                dto.getUsername(), dto.getOtp(), dto.getNewPassword());
        return ResponseEntity.ok(response);
    }
}
