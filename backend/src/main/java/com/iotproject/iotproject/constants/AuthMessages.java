package com.iotproject.iotproject.constants;

public class AuthMessages {
    // Registration messages
    public static final String REGISTER_SUCCESS = "User registered successfully";
    public static final String EMAIL_EXISTS = "Email already exists";
    public static final String REGISTRATION_DB_ERROR = "Registration failed due to database error";
    public static final String REGISTRATION_UNEXPECTED_ERROR = "Registration failed unexpectedly";
    public static final String OTP_SENT = "OTP sent successfully";
    public static final String OTP_SEND_FAILED = "Failed to send OTP";
    public static final String INVALID_OTP = "Invalid OTP code";

    // Login messages
    public static final String LOGIN_SUCCESS = "Login successful";
    public static final String EMAIL_NOT_REGISTERED = "Email not registered";
    public static final String INVALID_PASSWORD = "Invalid credentials"; // Generic for security
    public static final String INVALID_CREDENTIALS = "Invalid email or password"; // Combined message
    public static final String NOT_FOUND = "User Not Found";

    // Validation messages
    public static final String VALIDATION_FAILED = "Validation failed";
    public static final String DATABASE_ERROR = "Database operation failed";
    public static final String GENERIC_ERROR = "An error occurred";
    public static final String CORRECT_OTP = "Correct OTP";
}