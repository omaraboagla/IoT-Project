package com.iotproject.iotproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.iotproject.iotproject.enums.PollutionLevel;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMax;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirPollutionSensorDto {
    private String id;
//  @DecimalMax(value = "50.0", inclusive = true, message = "CO must be at most 50 ppm")
    private Double co;
//   @DecimalMax(value = "300.0", inclusive = true, message = "Ozone must be at most 300 ppb")
    private Double ozone;
    @Enumerated(EnumType.STRING)

    private String location;
    private PollutionLevel pollutionLevel;
    private LocalDateTime timestamp;
}
