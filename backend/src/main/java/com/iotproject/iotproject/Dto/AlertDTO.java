package com.iotproject.iotproject.Dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AlertDTO {
    private String type;
    private String message;
    private Double currentValue;
    private Double thresholdValue;
    private LocalDateTime triggeredAt;
}
