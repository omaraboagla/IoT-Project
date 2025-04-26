package com.iotproject.iotproject.Exception;

import com.iotproject.iotproject.Constants.AuthMessages;
import com.iotproject.iotproject.Dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
