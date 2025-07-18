package com.iotproject.iotproject.Entity;

import com.iotproject.iotproject.Enum.AlertType;
import com.iotproject.iotproject.Enum.SettingType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "settings")
public class Setting {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private SettingType type;

    private String metric;

    private float thresholdValue;

    @Enumerated(EnumType.STRING)
    private AlertType alertType;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
