package com.iotproject.iotproject.service;

import com.iotproject.iotproject.dto.AirPollutionSensorFilter;
import com.iotproject.iotproject.entity.AirPollutionSensor;
import com.iotproject.iotproject.enums.PollutionLevel;
import com.iotproject.iotproject.repo.AirPollutionSensorRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AirPollutionSensorService extends BaseSensorService<AirPollutionSensor, AirPollutionSensorFilter, AirPollutionSensorRepository> {

    private final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(StreetLightSensorService.class);

    public AirPollutionSensorService(AirPollutionSensorRepository repository) {
        super(repository);
    }

    public void generateAirPollutionReadings() {
        String[] locations = {"Alexandria", "Smart Village", "Nasr City", "5th Settlement"};
        String location = locations[random.nextInt(locations.length)];

        double co = random.nextDouble() * 50;
        double ozone = random.nextDouble() * 300;
        double pm2_5 = random.nextDouble() * 150;
        double pm10 = random.nextDouble() * 150;
        double no2 = random.nextDouble() * 100;
        double so2 = random.nextDouble() * 100;

        PollutionLevel pollutionLevel = determinePollutionLevel(co, ozone);

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

        repository.save(sensor);
    }

    public Page<AirPollutionSensor> filterAirPollutionData(AirPollutionSensorFilter filter, Pageable pageable) {
        Specification<AirPollutionSensor> spec = buildBaseSpecification(filter);

        // // Optional: filter by location
        // if (filter.getLocation() != null && !filter.getLocation().isEmpty()) {
        //     spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("location")), filter.getLocation().toLowerCase()));
        // }


        return filter(filter, pageable, spec);
    }

    private PollutionLevel determinePollutionLevel(double co, double ozone) {
        if (co > ozone) {
            if (co <= 9) return PollutionLevel.Good;
            else if (co <= 15) return PollutionLevel.Moderate;
            else if (co <= 25) return PollutionLevel.Unhealthy;
            else if (co <= 35) return PollutionLevel.Very_Unhealthy;
            else return PollutionLevel.Hazardous;
        } else {
            if (ozone <= 50) return PollutionLevel.Good;
            else if (ozone <= 100) return PollutionLevel.Moderate;
            else if (ozone <= 150) return PollutionLevel.Unhealthy;
            else if (ozone <= 200) return PollutionLevel.Very_Unhealthy;
            else return PollutionLevel.Hazardous;
        }
    }

  
}
