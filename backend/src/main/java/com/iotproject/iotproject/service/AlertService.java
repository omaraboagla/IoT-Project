package com.iotproject.iotproject.service;

import com.iotproject.iotproject.entity.AlertsEntity;
import com.iotproject.iotproject.repo.AlertRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public List<AlertsEntity> getAllAlerts() {
        return alertRepository.findAll();
    }

     public Page<AlertsEntity> getAllAlerts(Pageable pageable) {
        return alertRepository.findAll(pageable);
    }

    public AlertsEntity acknowledgeAlert(Long alertId) {
        AlertsEntity alert = alertRepository.findById(alertId).orElseThrow(
                () -> new IllegalArgumentException("Alert not found with id: " + alertId)
        );
        alert.setAcknowledged(true);
        return alertRepository.save(alert);
    }

     public Page<AlertsEntity> getAlertsByType(String type, Pageable pageable) {
        return alertRepository.findByType(type, pageable);
    }

}
