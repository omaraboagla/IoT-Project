package com.iotproject.iotproject.Dto;

import com.iotproject.iotproject.Enum.LightStatus;
import lombok.Data;

@Data
public class StreetLightSensorFilter extends BaseSensorFilter {
    private LightStatus status;
}
