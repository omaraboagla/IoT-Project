package com.iotproject.iotproject.Service;

import com.iotproject.iotproject.Constants.AuthMessages;
import com.iotproject.iotproject.Dto.*;
import com.iotproject.iotproject.Entity.User;
import com.iotproject.iotproject.Repo.UserRepository;
import com.iotproject.iotproject.Config.JwtService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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

    public ResponseDto register(RegisterDto registerDto) {
        try {
            if (userRepository.existsByEmail(registerDto.getEmail())) {
                return buildErrorResponse(AuthMessages.EMAIL_EXISTS);
            }

            User user = buildUserFromDto(registerDto);
            userRepository.save(user);

            return buildSuccessResponse(
                    jwtService.generateToken(user),
                    AuthMessages.REGISTER_SUCCESS
            );

        } catch (DataIntegrityViolationException e) {
            return buildErrorResponse(AuthMessages.REGISTRATION_DB_ERROR);
        } catch (ValidationException e) {
            return buildErrorResponse(AuthMessages.VALIDATION_FAILED);
        }
        catch (Exception e) {
            return buildErrorResponse(AuthMessages.REGISTRATION_UNEXPECTED_ERROR);
        }
    }

    public ResponseDto login(LoginDto loginDto) {
        try {
            if (!userRepository.existsByEmail(loginDto.getEmail())) {
                log.warn("Login attempt with unregistered email: {}", loginDto.getEmail());
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

    private User buildUserFromDto(RegisterDto dto) {
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