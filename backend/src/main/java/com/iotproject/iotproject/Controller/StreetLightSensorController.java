package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Dto.ApiResponseDto;
import com.iotproject.iotproject.Dto.StreetLightSensorDto;
import com.iotproject.iotproject.Dto.StreetLightSensorFilter;
import com.iotproject.iotproject.Entity.StreetLightSensor;
import com.iotproject.iotproject.Service.StreetLightSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class StreetLightSensorController extends BaseSensorController<StreetLightSensor , StreetLightSensorDto , StreetLightSensorFilter> {

    @Autowired
    private StreetLightSensorService streetLightSensorService;

    @PostMapping("/api/sensor/street-light/generate")
    public String generateStreetLightSensorData(){
        streetLightSensorService.generateStreetLightReading();
        return "Street Light Sensor data generated";
    }

    @Override
    protected Page<StreetLightSensor> filterEntities(StreetLightSensorFilter filter, Pageable pageable) {
        return streetLightSensorService.filterStreetLights(filter, pageable);
    }

    @GetMapping("/api/sensor/street-light/filter")
    public ResponseEntity<ApiResponseDto<List<StreetLightSensorDto>>> filterStreetLights(
            StreetLightSensorFilter filter,
            @PageableDefault(size = 10, sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable) {
        return filter(filter, StreetLightSensorDto.class ,pageable); // Call generic method from base class
    }


}
