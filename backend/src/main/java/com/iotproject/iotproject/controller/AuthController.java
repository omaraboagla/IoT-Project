package com.iotproject.iotproject.controller;

import jakarta.validation.Valid;

import com.iotproject.iotproject.constants.ApiPaths;
import com.iotproject.iotproject.dto.*;
import com.iotproject.iotproject.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.AUTH_BASE)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;



    @PostMapping(ApiPaths.INITIATE_SIGNUP)
    public ResponseEntity<ResponseDto> initiateSignup(@RequestBody @Valid SignupRequestDto dto) {
        ResponseDto response = authService.initiateSignup(dto);
        if (response.isSuccess()){
            return ResponseEntity.ok(response);

        }
        else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping(ApiPaths.COMPLETE_REGISTRATION)
    public ResponseEntity<ResponseDto> completeRegistration(
            @RequestBody @Valid CompleteRegistrationDto dto) {
        ResponseDto response = authService.completeRegistration(dto);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

 

    @PostMapping(ApiPaths.LOGIN)
    public ResponseEntity<ResponseDto> login(@RequestBody LoginDto loginDto) {
        ResponseDto response = authService.login(loginDto);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping(ApiPaths.VERIFY_OTP)
    public ResponseEntity<ResponseDto> verifyOtp(@RequestBody @Valid VerifyOtpDto dto) {
        ResponseDto response = authService.verifyOtp(dto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

}