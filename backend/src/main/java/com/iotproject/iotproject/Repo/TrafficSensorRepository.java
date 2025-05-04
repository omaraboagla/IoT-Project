package com.iotproject.iotproject.Repo;

import com.iotproject.iotproject.Entity.TrafficSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrafficSensorRepository extends JpaRepository<TrafficSensor, UUID> {
}
