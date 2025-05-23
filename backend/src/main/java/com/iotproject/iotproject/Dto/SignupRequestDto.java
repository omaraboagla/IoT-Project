package com.iotproject.iotproject.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupRequestDto {
    @NotBlank
    @Email
    private String email;
}
