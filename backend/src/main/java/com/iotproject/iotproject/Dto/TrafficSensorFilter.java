package com.iotproject.iotproject.Dto;

import com.iotproject.iotproject.Enum.CongestionLevel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class TrafficSensorFilter {
    private String location;
    private CongestionLevel congestionLevel;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;
}
