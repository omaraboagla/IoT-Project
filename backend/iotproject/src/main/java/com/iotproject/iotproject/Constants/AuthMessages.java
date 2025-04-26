package com.iotproject.iotproject.Constants;

public class AuthMessages {
    // Registration messages
    public static final String REGISTER_SUCCESS = "User registered successfully";
    public static final String EMAIL_EXISTS = "Email already exists";
    public static final String REGISTRATION_DB_ERROR = "Registration failed due to database error";
    public static final String REGISTRATION_UNEXPECTED_ERROR = "Registration failed unexpectedly";

    // Login messages
    public static final String LOGIN_SUCCESS = "Login successful";
    public static final String EMAIL_NOT_REGISTERED = "Email not registered";
    public static final String INVALID_PASSWORD = "Invalid credentials"; // Generic for security
    public static final String INVALID_CREDENTIALS = "Invalid email or password"; // Combined message

    // Validation messages
    public static final String VALIDATION_FAILED = "Validation failed";
}