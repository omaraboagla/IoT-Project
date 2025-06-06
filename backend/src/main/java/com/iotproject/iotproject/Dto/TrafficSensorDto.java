package com.iotproject.iotproject.Dto;

import com.iotproject.iotproject.Enum.CongestionLevel;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrafficSensorDto {
    private UUID id;
    private String location;
    private LocalDateTime timestamp;
    private int trafficDensity;
    private double avgSpeed;
    private CongestionLevel congestionLevel;
}
