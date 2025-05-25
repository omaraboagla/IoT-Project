package com.iotproject.iotproject.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SensorDataScheduler {

    @Autowired
    private RestTemplate restTemplate;

    private static final String TRAFFIC_SENSOR_API_URL = "http://host.docker.internal:8081/api/sensor/traffic-sensor/generate";
    private static final String STREET_LIGHT_SENSOR_API_URL = "http://host.docker.internal:8081/api/sensor/street-light/generate";
    private static final String AIR_POLLUTION_SENSOR_API_URL = "http://host.docker.internal:8081/api/sensor/air-pollution/generate";

    @Scheduled(cron = "0 */1 * * * *") // Every 5 minutes
    public void generateAndSaveSensorData() {
        restTemplate.postForObject(TRAFFIC_SENSOR_API_URL, null, String.class);
        System.out.println("Traffic sensor data generation triggered by scheduler.");

        restTemplate.postForObject(STREET_LIGHT_SENSOR_API_URL, null, String.class);
        System.out.println("Steet Light sensor data generation triggered by scheduler.");

        restTemplate.postForObject(AIR_POLLUTION_SENSOR_API_URL, null, String.class);
        System.out.println("Air pollution sensor data generation triggered by scheduler.");
    }
}
