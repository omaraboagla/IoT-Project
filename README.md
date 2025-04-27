IoT Project - Full Stack System


This is a full-stack IoT project with:
 Backend: Spring Boot (Java) for user authentication email, OTP, JWT security, and API management.
 Frontend: Angular (TypeScript) for the user interface.
 Testing: Manual test cases and bug reports for quality assurance.

Features
Backend (Spring Boot)
User Authentication

Email-based registration with OTP verification.

JWT token-based login.

Secure password hashing (BCrypt).

API Endpoints

/api/auth/initiate-signup -> Send OTP to email.

/api/auth/verify-otp -> Verify OTP before registration.

/api/auth/complete-registration -> Complete user signup.

/api/auth/login -> Authenticate and return JWT.

