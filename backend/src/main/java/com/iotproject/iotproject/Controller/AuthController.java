package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Dto.*;
import com.iotproject.iotproject.Service.AuthService;
import jakarta.validation.Valid;
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

//    @PostMapping("/register")
//    public ResponseEntity<ResponseDto> register(@RequestBody @Valid RegisterDto registerDto) {
//        ResponseDto response = authService.register(registerDto);
//
//        return ResponseEntity
//                .status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT)
//                .body(response);
//    }

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
    public ResponseEntity<ResponseDto> login(@RequestBody @Valid LoginDto loginDto) {
        ResponseDto response = authService.login(loginDto);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}