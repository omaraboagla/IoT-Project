package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Constants.ApiPaths;
import com.iotproject.iotproject.Dto.ApiResponseDto;
import com.iotproject.iotproject.Dto.AirPollutionSensorDto;
import com.iotproject.iotproject.Dto.AirPollutionSensorFilter;
import com.iotproject.iotproject.Entity.AirPollutionSensor;
import com.iotproject.iotproject.Service.AirPollutionSensorService;
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
