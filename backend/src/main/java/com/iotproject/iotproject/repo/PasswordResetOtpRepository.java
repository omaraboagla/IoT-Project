package com.iotproject.iotproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotproject.iotproject.entity.PasswordResetOtp;

import java.util.Optional;

public interface PasswordResetOtpRepository extends JpaRepository<PasswordResetOtp, Long> {
    Optional<PasswordResetOtp> findByEmailAndOtp(String email, String otp);
    void deleteByEmail(String email); // to remove old OTPs
}
