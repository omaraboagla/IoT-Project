package com.iotproject.iotproject.Service;

import com.iotproject.iotproject.Entity.AirPollutionSensor;
import com.iotproject.iotproject.Enum.PollutionLevel;
import com.iotproject.iotproject.Repo.AirPollutionSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AirPollutionSensorService {
    @Autowired
    private AirPollutionSensorRepository airPollutionSensorRepository;

    private final Random random = new Random();

    public void generateAirPollutionReadings() {
        String[] locations = {"Alexandria", "Smart Village", "Nasr City", "5th Settlement"};
        String location = locations[random.nextInt(locations.length)];

        // Generate values for the pollution attributes
        double co = random.nextDouble() * 50;
        double ozone = random.nextDouble() * 300;
        double pm2_5 = random.nextDouble() * 150;
        double pm10 = random.nextDouble() * 150;
        double no2 = random.nextDouble() * 100;
        double so2 = random.nextDouble() * 100; 

        PollutionLevel pollutionLevel = determinePollutionLevel(co, ozone);

        // Create and save AirPollutionSensor entity
        AirPollutionSensor sensor = new AirPollutionSensor();
        sensor.setLocation(location);
        sensor.setTimestamp(LocalDateTime.now());
        sensor.setCo(co);
        sensor.setOzone(ozone);
        sensor.setPm2_5(pm2_5);
        sensor.setPm10(pm10);
        sensor.setNo2(no2);
        sensor.setSo2(so2);
        sensor.setPollutionLevel(pollutionLevel);

        airPollutionSensorRepository.save(sensor);

        System.out.println("Air Pollution reading saved: " + sensor);
    }

    private PollutionLevel determinePollutionLevel(double co, double ozone) {
        if (co > ozone) {
            // Determine pollution level for CO
            if (co <= 9) return PollutionLevel.Good;
            else if (co <= 15) return PollutionLevel.Moderate;
            else if (co <= 25) return PollutionLevel.Unhealthy;
            else if (co <= 35) return PollutionLevel.Very_Unhealthy;
            else return PollutionLevel.Hazardous;
        } else {
            // Determine pollution level for ozone
            if (ozone <= 50) return PollutionLevel.Good;
            else if (ozone <= 100) return PollutionLevel.Moderate;
            else if (ozone <= 150) return PollutionLevel.Unhealthy;
            else if (ozone <= 200) return PollutionLevel.Very_Unhealthy;
            else return PollutionLevel.Hazardous;
        }
    }
}
