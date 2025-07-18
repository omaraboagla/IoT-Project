package com.iotproject.iotproject.Entity;

import com.iotproject.iotproject.Enum.CongestionLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "traffic_sensors_data")
@Data
@NoArgsConstructor
public class TrafficSensor {

    @Id
    private UUID id;

    @NotBlank(message = "Location must not be empty")
    private String location;

    @NotNull(message = "Timestamp must not be null")
    @PastOrPresent(message = "Timestamp must not be in the future")
    private LocalDateTime timestamp;

    @Min(0)
    @Max(500)
    private Integer trafficDensity;

    @DecimalMin("0.0")
    @DecimalMax("120.0")
    private Double avgSpeed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CongestionLevel congestionLevel;

    @PrePersist
    public void autoGenerateId() {
        this.id = UUID.randomUUID();
    }
}
