package com.iotproject.iotproject.Entity;

import com.iotproject.iotproject.Enum.AlertType;
import com.iotproject.iotproject.Enum.SettingType;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "settings")
public class Setting {

    @Id
    private UUID id;

    public void setId(UUID id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    private SettingType type; // Traffic, Air_Pollution, Street_Light

    private String metric; // e.g., Density, Speed, PM2.5

    public String getMetric() {
        return metric;
    }
    private float thresholdValue;

    public float getThresholdValue() {
        return thresholdValue;
    }

    @Enumerated(EnumType.STRING)
    private AlertType alertType; // above or below

    private LocalDateTime createdAt;
    public void setCreatedAt(LocalDateTime now) {
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}

