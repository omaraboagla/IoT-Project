package com.iotproject.iotproject.controller;

import com.iotproject.iotproject.constants.ApiPaths;
import com.iotproject.iotproject.dto.AirPollutionSensorDto;
import com.iotproject.iotproject.dto.AirPollutionSensorFilter;
import com.iotproject.iotproject.dto.ApiResponseDto;
import com.iotproject.iotproject.entity.AirPollutionSensor;
import com.iotproject.iotproject.service.AirPollutionSensorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AirPollutionSensorController extends BaseSensorController<AirPollutionSensor, AirPollutionSensorDto, AirPollutionSensorFilter> {

    @Autowired
    private AirPollutionSensorService airPollutionSensorService;

    @PostMapping(ApiPaths.AIR_POLLUTION_SENSOR)
    public String generateAirPollutionSensorData() {
        airPollutionSensorService.generateAirPollutionReadings();
        return "Air Pollution Sensor data generated";
    }

@Override
protected Page<AirPollutionSensor> filterEntities(AirPollutionSensorFilter filter, Pageable pageable) {
           return airPollutionSensorService.filterAirPollutionData(filter, pageable);
}

    @GetMapping(ApiPaths.FILTER_AIR_POLLUTION)
    public ResponseEntity<ApiResponseDto<List<AirPollutionSensorDto>>> filterAirPollutionSensors(
            AirPollutionSensorFilter filter,
            Pageable pageable) {
        return filter(filter, AirPollutionSensorDto.class, pageable);
    }
}
