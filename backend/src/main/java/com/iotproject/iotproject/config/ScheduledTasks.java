package com.iotproject.iotproject.Config;

import com.iotproject.iotproject.Service.AirPollutionSensorService;
import com.iotproject.iotproject.Service.StreetLightSensorService;
import com.iotproject.iotproject.Service.TrafficSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private TrafficSensorService trafficSensorService;

    @Autowired
    private AirPollutionSensorService airPollutionSensorService;

    @Autowired
    private StreetLightSensorService streetLightSensorService;

    @Scheduled(cron = "0 */5 * * * *")// 5 minutes
    public void execute() {
        trafficSensorService.generateTrafficReadings();
        airPollutionSensorService.generateAirPollutionReadings();
        streetLightSensorService.generateStreetLightReadings();
    }
}
