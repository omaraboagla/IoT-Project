package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Constants.ApiPaths;
import com.iotproject.iotproject.Service.AirPollutionSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirPollutionSensorController {

    @Autowired
    private AirPollutionSensorService airPollutionSensorService;

    @PostMapping(ApiPaths.AIR_POLLUTION_SENSOR)
    public String generateAirPollutionSensorData(){
    airPollutionSensorService.generateAirPollutionReadings();
        return "Air Pollution Sensor data generated";
    }
}
