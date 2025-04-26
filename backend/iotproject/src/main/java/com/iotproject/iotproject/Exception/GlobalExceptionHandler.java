package com.iotproject.iotproject.Exception;

import com.iotproject.iotproject.Constants.AuthMessages;
import com.iotproject.iotproject.Dto.ResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.builder()
                        .success(false)
                        .token(null)
                        .message(AuthMessages.VALIDATION_FAILED)
                        .build());
    }

    @ExceptionHandler(AuthServiceException.class)
    public ResponseEntity<ResponseDto> handleAuthServiceException(AuthServiceException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .build());
    }



    // Handle invalid credentials
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDto> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResponseDto.builder()
                        .success(false)
                        .message(AuthMessages.INVALID_CREDENTIALS)
                        .build());
    }

    // Handle database integrity violations
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ResponseDto.builder()
                        .success(false)
                        .message(AuthMessages.DATABASE_ERROR)
                        .build());
    }







}
