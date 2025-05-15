package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Dto.ApiResponseDto;
import com.iotproject.iotproject.Dto.TrafficSensorDto;
import com.iotproject.iotproject.Dto.TrafficSensorFilter;
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
public class TrafficSensorController {

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

    @GetMapping("/api/sensor/traffic-sensor/all")
    public ResponseEntity<ApiResponseDto<List<TrafficSensorDto>>> getAllTrafficSensorData() {
        try {
            List<TrafficSensor> sensors = trafficSensorService.getAllTrafficSensorData();

            List<TrafficSensorDto> dtoList = sensors.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponseDto<>("Success", dtoList, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>("Failed to fetch traffic sensor data.", null, null));
        }
    }

    @GetMapping("/api/sensor/traffic-sensor/filter")
    public ResponseEntity<ApiResponseDto<List<TrafficSensorDto>>> filterTrafficSensors(
            TrafficSensorFilter filter,
            @PageableDefault(size = 10, sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            Page<TrafficSensor> page = trafficSensorService.filterTrafficSensors(filter, pageable);

            List<TrafficSensorDto> dtoList = page.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            Map<String, Object> meta = Map.of(
                    "pageNumber", page.getNumber(),
                    "pageSize", page.getSize(),
                    "totalElements", page.getTotalElements(),
                    "totalPages", page.getTotalPages(),
                    "isLast", page.isLast()
            );

            return ResponseEntity.ok(new ApiResponseDto<>("Success", dtoList, meta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>("Failed to filter traffic sensor data.", null, null));
        }
    }

    private TrafficSensorDto toDto(TrafficSensor entity) {
        return new TrafficSensorDto(
                entity.getId(),
                entity.getLocation(),
                entity.getTimestamp(),
                entity.getTrafficDensity(),
                entity.getAvgSpeed(),
                entity.getCongestionLevel()
        );
    }
}
