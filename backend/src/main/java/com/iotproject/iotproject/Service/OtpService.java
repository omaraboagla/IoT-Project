package com.iotproject.iotproject.Service;

import com.iotproject.iotproject.Repo.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    private Map<String, String> otpStorage;

    @PostConstruct
    public void init() {
        otpStorage = new HashMap<>();
    }

    public void generateAndSendOtp(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email not found");
        }

        String otp = String.format("%06d", new Random().nextInt(1000000));
        otpStorage.put(email, otp);
        sendEmail(email, otp);
    }

    private void sendEmail(String toEmail, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Password Reset OTP");
            helper.setText("Your OTP for password reset is: " + otp);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public boolean verifyOtp(String email, String otp) {
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(otp);
    }

    public void clearOtp(String email) {
        otpStorage.remove(email);
    }
}
