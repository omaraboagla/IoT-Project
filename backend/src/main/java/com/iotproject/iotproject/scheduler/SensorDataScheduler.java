package com.iotproject.iotproject.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.iotproject.iotproject.constants.ApiPaths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SensorDataScheduler {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${SENSOR_API_BASE_URL}")
    private String baseUrl;

    @Value("${SENSOR_SCHEDULER_CRON}")
    private String sensorSchedulerCron;

    private static final Logger logger = LoggerFactory.getLogger(SensorDataScheduler.class);

    @Scheduled(cron = "${SENSOR_SCHEDULER_CRON:0 */1 * * * *}")
    public void generateAndSaveSensorData() {
        restTemplate.postForObject(baseUrl + ApiPaths.TRAFFIC_SENSOR, null, String.class);
        logger.info("Traffic sensor data generation triggered by scheduler.");

        restTemplate.postForObject(baseUrl + ApiPaths.STREET_LIGHT_SENSOR, null, String.class);
        logger.info("Street light sensor data generation triggered by scheduler.");

        restTemplate.postForObject(baseUrl + ApiPaths.AIR_POLLUTION_SENSOR, null, String.class);
        logger.info("Air pollution sensor data generation triggered by scheduler.");
    }
}
