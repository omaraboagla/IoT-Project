package com.iotproject.iotproject.dto;

import com.iotproject.iotproject.enums.LightStatus;

import lombok.Data;

@Data
public class StreetLightSensorFilter extends BaseSensorFilter {
    private LightStatus status;
}
