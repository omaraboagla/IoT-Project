package com.iotproject.iotproject.Service;

import com.iotproject.iotproject.Entity.AlertsEntity;
import com.iotproject.iotproject.Repo.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public List<AlertsEntity> getAllAlerts() {
        return alertRepository.findAll();
    }

    public AlertsEntity acknowledgeAlert(Long alertId) {
        AlertsEntity alert = alertRepository.findById(alertId).orElseThrow(
                () -> new IllegalArgumentException("Alert not found with id: " + alertId)
        );
        alert.setAcknowledged(true);
        return alertRepository.save(alert);
    }
}
