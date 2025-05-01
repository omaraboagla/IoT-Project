package com.iotproject.iotproject.Repo;

import com.iotproject.iotproject.Entity.StreetLightSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StreetLightSensorRepository extends JpaRepository<StreetLightSensor, UUID> {
}
