package com.iotproject.iotproject.Repo;

import com.iotproject.iotproject.Entity.AirPollutionSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirPollutionSensorRepository extends JpaRepository<AirPollutionSensor, Long> {
}
