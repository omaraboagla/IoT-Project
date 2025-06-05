package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Dto.*;
import com.iotproject.iotproject.Entity.TrafficSensor;
import com.iotproject.iotproject.Service.TrafficSensorService;
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
public class TrafficSensorController extends BaseSensorController<TrafficSensor,TrafficSensorDto,TrafficSensorFilter> {

    @Autowired
    private TrafficSensorService trafficSensorService;

    @PostMapping("/api/sensor/traffic-sensor/generate")
    public ResponseEntity<ApiResponseDto<String>> generateTrafficSensorData() {
        try {
            trafficSensorService.generateTrafficReadings();
            return ResponseEntity.ok(new ApiResponseDto<>("Traffic sensor data generation triggered.", null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>("Failed to generate traffic sensor data.", null, null));
        }
    }





    @Override
    protected Page<TrafficSensor> filterEntities(TrafficSensorFilter filter, Pageable pageable) {
        return trafficSensorService.filterTrafficSensors(filter, pageable);
    }



    @GetMapping("/api/sensor/traffic-sensor/filter")
    public ResponseEntity<ApiResponseDto<List<TrafficSensorDto>>> filterTrafficSensors(
            TrafficSensorFilter filter,
            @PageableDefault(size = 10, sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable) {
        return filter(filter, TrafficSensorDto.class ,pageable); // Call generic method from base class
    }
}
