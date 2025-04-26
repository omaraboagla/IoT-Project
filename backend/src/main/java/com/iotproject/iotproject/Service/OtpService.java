package com.iotproject.iotproject.Service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final JavaMailSender mailSender;
    private final CacheManager cacheManager;

    // Generate and send OTP
    public void sendOtp(String email) {
        try {
            String otp = generateOtp();
            Cache cache = cacheManager.getCache("otpCache");
            cache.put(email, otp);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("no-reply@iotproject.com");  // Required field
            message.setTo(email);
            message.setSubject("Your OTP Code");
            message.setText("Your verification code is: " + otp);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("OTP sending failed: " + e.getMessage());
        }
    }

    // Verify OTP
    public boolean verifyOtp(String email, String userEnteredOtp) {
        Cache cache = cacheManager.getCache("otpCache");
        String storedOtp = cache.get(email, String.class);
        return storedOtp != null && storedOtp.equals(userEnteredOtp);
    }

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}