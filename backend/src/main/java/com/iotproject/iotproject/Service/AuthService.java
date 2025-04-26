package com.iotproject.iotproject.Service;

import com.iotproject.iotproject.Dto.LoginDto;
import com.iotproject.iotproject.Dto.RegisterDto;
import com.iotproject.iotproject.Dto.ResponseDto;
import com.iotproject.iotproject.Entity.User;
import com.iotproject.iotproject.Repo.UserRepository;
import com.iotproject.iotproject.Config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseDto register(RegisterDto registerDto) {
        try {
            if (userRepository.existsByEmail(registerDto.getEmail())) {
                return ResponseDto.builder()
                        .success(false)
                        .message("Email already exists")
                        .build();
            }

            User user = User.builder()
                    .username(registerDto.getUsername())
                    .email(registerDto.getEmail())
                    .password(passwordEncoder.encode(registerDto.getPassword()))
                    .build();

            userRepository.save(user);

            String jwtToken = jwtService.generateToken(user);

            return ResponseDto.builder()
                    .token(jwtToken)
                    .success(true)
                    .message("User registered successfully")
                    .build();


        } catch (DataIntegrityViolationException e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Registration failed due to database error")
                    .build();
        }
        catch (Exception e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Registration failed unexpectedly")
                    .build();
        }




    }

    public ResponseDto login(LoginDto loginDto) {

        if (!userRepository.existsByEmail(loginDto.getEmail())) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Email not registered")
                    .build();
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );

            User user = userRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String jwtToken = jwtService.generateToken(user);

            return ResponseDto.builder()
                    .token(jwtToken)
                    .success(true)
                    .message("Login successful")
                    .build();
        } catch (BadCredentialsException e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Invalid password")
                    .build();
        }

    }
}