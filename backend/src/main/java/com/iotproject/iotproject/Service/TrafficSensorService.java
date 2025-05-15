package com.iotproject.iotproject.Service;

import com.iotproject.iotproject.Dto.TrafficSensorFilter;
import com.iotproject.iotproject.Entity.TrafficSensor;
import com.iotproject.iotproject.Enum.CongestionLevel; // Import the enum
import com.iotproject.iotproject.Repo.TrafficSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    public List<TrafficSensor> getAllTrafficSensorData() {
        return trafficSensorRepository.findAll();
    }

    public Page<TrafficSensor> filterTrafficSensors(TrafficSensorFilter filter, Pageable pageable) {
        Specification<TrafficSensor> spec = Specification.where(null);

        if (filter.getLocation() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("location"), filter.getLocation()));
        }

        if (filter.getCongestionLevel() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("congestionLevel"), filter.getCongestionLevel()));
        }

        if (filter.getStartDate() != null && filter.getEndDate() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.between(root.get("timestamp"), filter.getStartDate(), filter.getEndDate()));
        }

        return trafficSensorRepository.findAll(spec, pageable);
    }

}
