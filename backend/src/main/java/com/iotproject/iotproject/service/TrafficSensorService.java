package com.iotproject.iotproject.service;

import com.iotproject.iotproject.dto.TrafficSensorDto;
import com.iotproject.iotproject.dto.TrafficSensorFilter;
import com.iotproject.iotproject.entity.TrafficSensor;
import com.iotproject.iotproject.enums.CongestionLevel;
import com.iotproject.iotproject.repo.TrafficSensorRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class TrafficSensorService extends BaseSensorService<TrafficSensor ,  Object , TrafficSensorRepository>{

    @Autowired
    private TrafficSensorRepository trafficSensorRepository;

    private final Random random = new Random();

    private static final Logger logger = LoggerFactory.getLogger(TrafficSensorService.class);


    public TrafficSensorService(TrafficSensorRepository repository) {
        super(repository);
    }

    public void generateTrafficReadings() {
      String location = getRandomLocation();

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
        logger.info("Traffic reading saved: {}", reading);
    }

    public List<TrafficSensor> getAllTrafficSensorData() {
        return trafficSensorRepository.findAll();
    }

    public Page<TrafficSensor> filterTrafficSensors(TrafficSensorFilter filter, Pageable pageable) {
        Specification<TrafficSensor> spec = buildBaseSpecification(filter);


        if (filter.getCongestionLevel() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("congestionLevel"), filter.getCongestionLevel()));
        }


        return filter(filter , pageable , spec);
    }

}
