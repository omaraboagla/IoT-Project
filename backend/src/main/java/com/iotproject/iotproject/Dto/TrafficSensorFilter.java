package com.iotproject.iotproject.Dto;

import com.iotproject.iotproject.Enum.CongestionLevel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class TrafficSensorFilter extends BaseSensorFilter {
    private CongestionLevel congestionLevel;


}
