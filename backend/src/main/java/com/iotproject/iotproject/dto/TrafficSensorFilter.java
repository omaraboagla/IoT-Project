package com.iotproject.iotproject.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import com.iotproject.iotproject.enums.CongestionLevel;

import java.time.LocalDateTime;

@Data
public class TrafficSensorFilter extends BaseSensorFilter {
    private CongestionLevel congestionLevel;


}
