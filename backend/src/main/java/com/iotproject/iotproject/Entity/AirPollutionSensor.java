package com.iotproject.iotproject.Entity;

import com.iotproject.iotproject.Enum.PollutionLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "air_pollution_sensors_data")
@Data
@NoArgsConstructor
public class AirPollutionSensor {

    @Id
    private UUID id;

    @NotBlank(message = "Location must not be empty")
    private String location;

    @PastOrPresent(message = "Timestamp cannot be in the future")
    private LocalDateTime timestamp;

    @DecimalMin(value = "0.0", inclusive = true, message = "CO must be at least 0 ppm")
    @DecimalMax(value = "50.0", inclusive = true, message = "CO must be at most 50 ppm")
    private Double co;

    @DecimalMin(value = "0.0", inclusive = true, message = "Ozone must be at least 0 ppb")
    @DecimalMax(value = "300.0", inclusive = true, message = "Ozone must be at most 300 ppb")
    private Double ozone;

    private Double pm2_5;
    private Double pm10;
    private Double no2;
    private Double so2;

    @Enumerated(EnumType.STRING)
    private PollutionLevel pollutionLevel;

    @PrePersist
    public void assignId() {
        this.id = UUID.randomUUID();
    }
}
