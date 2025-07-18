package com.iotproject.iotproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.iotproject.iotproject.enums.LightStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreetLightSensorDto {
    private String location;
    private LightStatus status;
    private int brightnessLevel;
    private double powerConsumption;
    private LocalDateTime timestamp;
}

