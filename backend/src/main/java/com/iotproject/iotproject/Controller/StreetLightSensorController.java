package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Service.StreetLightSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreetLightSensorController {

    @Autowired
    private StreetLightSensorService streetLightSensorService;

    @PostMapping("/api/sensor/street-light/generate")
    public String generateStreetLightSensorData(){
        streetLightSensorService.generateStreetLightReadings();
        return "Street Light Sensor data generated";
    }

}
