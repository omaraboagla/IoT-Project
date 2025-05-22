package com.iotproject.iotproject.Repo;

import com.iotproject.iotproject.Entity.AlertsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<AlertsEntity, Long> {
}
