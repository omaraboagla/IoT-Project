package com.iotproject.iotproject.Dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class AirPollutionSensorFilter extends BaseSensorFilter{

    // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    // private LocalDateTime startTime;

    // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    // private LocalDateTime endTime;

    // // Optional filtering
    // private String location;

    // private Boolean sortByHighestCO;
    // private Boolean sortByHighestOzone;
}
