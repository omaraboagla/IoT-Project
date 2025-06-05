package com.iotproject.iotproject.Dto;

import com.iotproject.iotproject.Enum.LightStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

