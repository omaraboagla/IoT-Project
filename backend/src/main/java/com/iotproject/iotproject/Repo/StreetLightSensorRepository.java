package com.iotproject.iotproject.Repo;

import com.iotproject.iotproject.Entity.StreetLightSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StreetLightSensorRepository extends JpaRepository<StreetLightSensor, UUID>, JpaSpecificationExecutor<StreetLightSensor> {
    Optional<StreetLightSensor> findTopByOrderByTimestampDesc();
    List<StreetLightSensor> findAllByTimestampAfter(LocalDateTime time);

}
