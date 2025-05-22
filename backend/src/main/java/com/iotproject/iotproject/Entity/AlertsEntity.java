package com.iotproject.iotproject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // e.g., "TRAFFIC_DENSITY", "AVERAGE_SPEED"

    private String message;

    private Double currentValue;

    private Double thresholdValue;

    private LocalDateTime triggeredAt;

    private boolean acknowledged = false; // for UI if needed
}
