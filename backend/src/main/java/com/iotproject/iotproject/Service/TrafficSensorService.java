package com.iotproject.iotproject.Service;

import com.iotproject.iotproject.Entity.TrafficSensor;
import com.iotproject.iotproject.Enum.CongestionLevel; // Import the enum
import com.iotproject.iotproject.Repo.TrafficSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class TrafficSensorService {

    @Autowired
    private TrafficSensorRepository trafficSensorRepository;

    private final Random random = new Random();

    public void generateTrafficReadings() {
        String[] locations = {"Alexandria", "Smart Village", "Nasr City", "5th Settlement"};
        String location = locations[random.nextInt(locations.length)];

        int trafficDensity = random.nextInt(501);
        double avgSpeed = 0 + (120.0 * random.nextDouble());

        CongestionLevel congestionLevel;
        if (trafficDensity >= 400) {
            congestionLevel = CongestionLevel.SEVERE;
        } else if (trafficDensity >= 250) {
            congestionLevel = CongestionLevel.HIGH;
        } else if (trafficDensity >= 100) {
            congestionLevel = CongestionLevel.MODERATE;
        } else {
            congestionLevel = CongestionLevel.LOW;
        }

        TrafficSensor reading = new TrafficSensor();
        reading.setLocation(location);
        reading.setTimestamp(LocalDateTime.now());
        reading.setTrafficDensity(trafficDensity);
        reading.setAvgSpeed(avgSpeed);
        reading.setCongestionLevel(congestionLevel);

        trafficSensorRepository.save(reading);
        System.out.println("Traffic reading saved: " + reading);
    }
}
