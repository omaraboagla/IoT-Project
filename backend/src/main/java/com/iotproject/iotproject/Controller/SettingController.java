package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Entity.Setting;
import com.iotproject.iotproject.Enum.AlertType;
import com.iotproject.iotproject.Enum.SettingType;
import com.iotproject.iotproject.Repo.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/settings")
public class SettingController {

    @Autowired
    private SettingRepository settingRepository;

    @PostMapping
    public ResponseEntity<?> createSetting(@RequestBody Setting setting) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        if (!validateSetting(setting)) {
            return ResponseEntity.badRequest().body("Invalid setting values or metric out of allowed range.");
        }

        setting.setId(UUID.randomUUID());
        setting.setCreatedAt(LocalDateTime.now());

        settingRepository.save(setting);

        return ResponseEntity.ok("Setting saved successfully.");
    }

    private boolean validateSetting(Setting setting) {
        if (setting.getThresholdValue() < 0) return false;

        switch (setting.getMetric()) {
            case "trafficDensity":
                return setting.getThresholdValue() <= 500;
            case "avgSpeed":
                return setting.getThresholdValue() <= 120;
            case "co":
                return setting.getThresholdValue() <= 50;
            case "ozone":
                return setting.getThresholdValue() <= 300;
            case "brightnessLevel":
                return setting.getThresholdValue() <= 100;
            case "powerConsumption":
                return setting.getThresholdValue() <= 5000;
            default:
                return false;
        }
    }
}
