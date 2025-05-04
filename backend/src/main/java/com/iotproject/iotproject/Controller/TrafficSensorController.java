package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Service.TrafficSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrafficSensorController {

    @Autowired
    private TrafficSensorService trafficSensorService;

    @PostMapping("/api/sensor/traffic-sensor/generate")
    public String generateTrafficSensorData() {
        trafficSensorService.generateTrafficReadings();
        return "Traffic sensor data generation triggered.";
    }
}
