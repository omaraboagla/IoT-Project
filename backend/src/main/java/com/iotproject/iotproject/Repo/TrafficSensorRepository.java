package com.iotproject.iotproject.Repo;

import com.iotproject.iotproject.Entity.TrafficSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrafficSensorRepository extends JpaRepository<TrafficSensor, UUID>, JpaSpecificationExecutor<TrafficSensor> {
    Optional<TrafficSensor> findTopByOrderByTimestampDesc();
    List<TrafficSensor> findAllByTimestampAfter(LocalDateTime time);

}
