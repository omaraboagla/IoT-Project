package com.iotproject.iotproject.service;

import com.iotproject.iotproject.dto.StreetLightSensorDto;
import com.iotproject.iotproject.dto.StreetLightSensorFilter;
import com.iotproject.iotproject.entity.StreetLightSensor;
import com.iotproject.iotproject.enums.LightStatus;
import com.iotproject.iotproject.repo.StreetLightSensorRepository;
import com.iotproject.iotproject.service.BaseSensorService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StreetLightSensorService extends BaseSensorService<StreetLightSensor, Object,StreetLightSensorRepository> {

    private final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(StreetLightSensorService.class);


    public StreetLightSensorService(StreetLightSensorRepository repository) {
        super(repository);
    }

    public void generateStreetLightReading() {
        String location = getRandomLocation();

        int brightness = random.nextInt(101);
        double power = random.nextDouble() * 5000;
        LightStatus status = (brightness > 10) ? LightStatus.ON : LightStatus.OFF;

        StreetLightSensor sensor = new StreetLightSensor();
        sensor.setLocation(location);
        sensor.setTimestamp(LocalDateTime.now());
        sensor.setBrightnessLevel(brightness);
        sensor.setPowerConsumption(power);
        sensor.setStatus(status);

        save(sensor);
        logger.info("Street reading saved: {}", sensor);

    }

    public Page<StreetLightSensor> filterStreetLights(StreetLightSensorFilter filter, Pageable pageable) {
        Specification<StreetLightSensor> spec = buildBaseSpecification(filter);

        if (filter.getStatus() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), filter.getStatus()));
        }

        return filter(filter, pageable, spec);
    }


}
