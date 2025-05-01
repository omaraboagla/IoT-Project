package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Service.AirPollutionSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirPollutionSensorController {

    @Autowired
    private AirPollutionSensorService airPollutionSensorService;

    @PostMapping("/api/sensor/air-pollution/generate")
    public String generateAirPollutionSensorData(){
        airPollutionSensorService.generateAirPollutionReadings();
        return "Air Pollution Sensor data generated";
    }
}
