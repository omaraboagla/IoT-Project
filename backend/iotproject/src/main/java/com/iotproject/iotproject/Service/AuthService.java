package com.iotproject.iotproject.Service;

import com.iotproject.iotproject.Constants.AuthMessages;
import com.iotproject.iotproject.Dto.*;
import com.iotproject.iotproject.Entity.User;
import com.iotproject.iotproject.Exception.AuthServiceException;
import com.iotproject.iotproject.Repo.UserRepository;
import com.iotproject.iotproject.Config.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final OtpService otpService;


    public ResponseDto initiateSignup(SignupRequestDto dto) {
        try {
            if (userRepository.existsByEmail(dto.getEmail())) {
                return buildErrorResponse(AuthMessages.EMAIL_EXISTS);
            }

            otpService.sendOtp(dto.getEmail());
            return buildSuccessResponse(null,AuthMessages.OTP_SENT);

        } catch (MailException e) {
            throw new AuthServiceException(AuthMessages.OTP_SEND_FAILED);
        } catch (Exception e) {
            log.error("Unexpected error during signup for {}: {}", e.getMessage());
            log.error("Exception class: {}", e.getClass().getName());
            log.debug("Full stack trace: ", e);
            throw new AuthServiceException(AuthMessages.GENERIC_ERROR);
        }
    }

    public ResponseDto completeRegistration(CompleteRegistrationDto dto) {
        try {
            if (!otpService.verifyOtp(dto.getEmail(), dto.getOtp())) {
                throw new AuthServiceException(AuthMessages.INVALID_OTP);
            }

            User user = buildUserFromDto(dto);
            userRepository.save(user);

            String jwtToken = jwtService.generateToken(user);
            return buildSuccessResponse(jwtToken , AuthMessages.REGISTER_SUCCESS);

        } catch (DataIntegrityViolationException e) {
            throw new AuthServiceException(AuthMessages.DATABASE_ERROR);
        }
    }

    public ResponseDto login(LoginDto loginDto) {
        try {
            if (!userRepository.existsByEmail(loginDto.getEmail())) {
                return buildErrorResponse(AuthMessages.EMAIL_NOT_REGISTERED);
            }

            authenticateUser(loginDto);
            User user = findUserByEmail(loginDto.getEmail());

            return buildSuccessResponse(
                    jwtService.generateToken(user),
                    AuthMessages.LOGIN_SUCCESS
            );

        } catch (BadCredentialsException e) {
            return buildErrorResponse(AuthMessages.INVALID_CREDENTIALS); // Generic message
        }
    }

    private User buildUserFromDto(CompleteRegistrationDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
    }

    private void authenticateUser(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private ResponseDto buildSuccessResponse(String token, String message) {
        return ResponseDto.builder()
                .success(true)
                .message(message)
                .token(token)
                .build();
    }

    private ResponseDto buildErrorResponse(String message) {
        return ResponseDto.builder()
                .success(false)
                .message(message)
                .build();
    }
}