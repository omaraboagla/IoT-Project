package com.iotproject.iotproject.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iotproject.iotproject.entity.AlertsEntity;

public interface AlertRepository extends JpaRepository<AlertsEntity, Long> {
        Page<AlertsEntity> findByType(String type, Pageable pageable);
}
