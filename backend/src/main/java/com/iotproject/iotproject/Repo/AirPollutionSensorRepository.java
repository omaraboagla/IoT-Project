package com.iotproject.iotproject.Repo;

import com.iotproject.iotproject.Entity.AirPollutionSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AirPollutionSensorRepository extends JpaRepository<AirPollutionSensor, UUID>, JpaSpecificationExecutor<AirPollutionSensor> {
    Optional<AirPollutionSensor> findTopByOrderByTimestampDesc();
    List<AirPollutionSensor> findAllByTimestampAfter(LocalDateTime time);

}
