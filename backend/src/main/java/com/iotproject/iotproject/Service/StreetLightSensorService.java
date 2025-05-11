package com.iotproject.iotproject.Service;

import com.iotproject.iotproject.Entity.StreetLightSensor;
import com.iotproject.iotproject.Enum.LightStatus;
import com.iotproject.iotproject.Repo.StreetLightSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class StreetLightSensorService {

    @Autowired
    private StreetLightSensorRepository streetLightSensorRepository;

    private final Random random = new Random();

    public void generateStreetLightReadings() {
        String[] locations = {"Alexandria", "Smart Village", "Nasr City", "5th Settlement"};
        String location = locations[random.nextInt(locations.length)];

        int brightnessLevel = random.nextInt(101); // 0–100
        double powerConsumption = random.nextDouble() * 5000; // 0–5000
        LightStatus status = (brightnessLevel > 10) ? LightStatus.ON : LightStatus.OFF;

        StreetLightSensor sensor = new StreetLightSensor();
        sensor.setLocation(location);
        sensor.setTimestamp(LocalDateTime.now());
        sensor.setBrightnessLevel(brightnessLevel);
        sensor.setPowerConsumption(powerConsumption);
        sensor.setStatus(status);

        streetLightSensorRepository.save(sensor);

        System.out.println("Street Light Sensor Reading Saved: " + sensor);

    }
}
