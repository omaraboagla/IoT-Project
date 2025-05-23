package com.iotproject.iotproject.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompleteRegistrationDto {
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    private String otp;

}