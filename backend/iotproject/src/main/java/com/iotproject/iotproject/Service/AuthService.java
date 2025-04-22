package com.iotproject.iotproject.Service;

import com.iotproject.iotproject.Dto.LoginDto;
import com.iotproject.iotproject.Dto.RegisterDto;
import com.iotproject.iotproject.Dto.ResponseDto;
import com.iotproject.iotproject.Entity.User;
import com.iotproject.iotproject.Repo.UserRepository;
import com.iotproject.iotproject.Config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
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

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return ResponseDto.builder()
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
                .message("User registered successfully")
                .build();
    }

    public ResponseDto login(LoginDto loginDto) {
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
                .message("Login successful")
                .build();
    }
}