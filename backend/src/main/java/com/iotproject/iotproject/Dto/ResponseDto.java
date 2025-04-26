package com.iotproject.iotproject.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;
    private String message;
}