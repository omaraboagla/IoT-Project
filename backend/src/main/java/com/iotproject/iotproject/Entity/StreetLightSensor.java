package com.iotproject.iotproject.Entity;

import com.iotproject.iotproject.Enum.LightStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "street_light_sensors_data")
@Data
@NoArgsConstructor
public class StreetLightSensor {

    @Id
    private UUID id;

    @NotBlank
    private String location;

    @NotNull(message = "Timestamp must not be null")
    @PastOrPresent
    private LocalDateTime timestamp;

    @Min(0) @Max(100)
    private Integer brightnessLevel;

    @DecimalMin("0.0") @DecimalMax("5000.0")
    private Double powerConsumption;

    @Enumerated(EnumType.STRING)
    private LightStatus status;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID();
    }
}
