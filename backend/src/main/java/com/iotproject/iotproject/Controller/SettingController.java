package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Constants.ApiPaths;
import com.iotproject.iotproject.Constants.MetricConstants;
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
@RequestMapping(ApiPaths.SETTING_BASE)
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
            case MetricConstants.TRAFFIC_DENSITY:
                return setting.getThresholdValue() <= 500;
            case MetricConstants.AVG_SPEED:
                return setting.getThresholdValue() <= 120;
            case MetricConstants.CO:
                return setting.getThresholdValue() <= 50;
            case MetricConstants.OZONE:
                return setting.getThresholdValue() <= 300;
            case MetricConstants.BRIGHTNESS_LEVEL:
                return setting.getThresholdValue() <= 100;
            case MetricConstants.POWER_CONSUMPTION:
                return setting.getThresholdValue() <= 5000;
            default:
                return false;
        }
    }
}
