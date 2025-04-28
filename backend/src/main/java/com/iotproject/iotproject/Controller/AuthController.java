package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Dto.*;
import jakarta.validation.Valid;

import com.iotproject.iotproject.Dto.LoginDto;
import com.iotproject.iotproject.Dto.RegisterDto;
import com.iotproject.iotproject.Dto.ResponseDto;
import com.iotproject.iotproject.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;



    @PostMapping("/initiate-signup")
    public ResponseEntity<ResponseDto> initiateSignup(@RequestBody @Valid SignupRequestDto dto) {
        ResponseDto response = authService.initiateSignup(dto);
        if (response.isSuccess()){
            return ResponseEntity.ok(response);

        }
        else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/complete-registration")
    public ResponseEntity<ResponseDto> completeRegistration(
            @RequestBody @Valid CompleteRegistrationDto dto) {
        ResponseDto response = authService.completeRegistration(dto);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

 

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginDto loginDto) {
        ResponseDto response = authService.login(loginDto);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ResponseDto> verifyOtp(@RequestBody @Valid VerifyOtpDto dto) {
        ResponseDto response = authService.verifyOtp(dto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

}