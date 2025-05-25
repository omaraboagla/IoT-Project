package com.iotproject.iotproject.Scheduler;

import com.iotproject.iotproject.Entity.AlertsEntity;
import com.iotproject.iotproject.Entity.Setting;
import com.iotproject.iotproject.Enum.AlertType;
import com.iotproject.iotproject.Enum.SettingType;
import com.iotproject.iotproject.Repo.AlertRepository;
import com.iotproject.iotproject.Repo.SettingRepository;
import com.iotproject.iotproject.Repo.TrafficSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AlertScheduler {

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private TrafficSensorRepository trafficRepo;

    @Autowired
    private AlertRepository alertRepository;



    @Scheduled(cron = "0 */1 * * * *") // 10 seconds into every minute
    public void evaluateTrafficAlerts() {
        Setting latestSetting = settingRepository.findTopByOrderByCreatedAtDesc();

        if (latestSetting == null) {
            System.out.println("No settings found.");
            return;
        }

        if (latestSetting.getType() != SettingType.Traffic) {
            System.out.println("Latest setting is not related to traffic.");
            return;
        }

        switch (latestSetting.getMetric()) {
            case "trafficDensity":
                trafficRepo.findTopByOrderByTimestampDesc().ifPresent(sensor -> {
                    Double currentValue = Double.valueOf(sensor.getTrafficDensity());
                    checkAndCreateAlert(latestSetting, currentValue);
                });
                break;

            case "avgSpeed":
                trafficRepo.findTopByOrderByTimestampDesc().ifPresent(sensor -> {
                    Double currentValue = sensor.getAvgSpeed();
                    checkAndCreateAlert(latestSetting, currentValue);
                });
                break;

            default:
                System.out.println("Unknown metric: " + latestSetting.getMetric());
        }
    }

    private void checkAndCreateAlert(Setting setting, Double currentValue) {
        boolean shouldAlert = false;

        if (setting.getAlertType() == AlertType.above && currentValue > setting.getThresholdValue()) {
            shouldAlert = true;
        } else if (setting.getAlertType() == AlertType.below && currentValue < setting.getThresholdValue()) {
            shouldAlert = true;
        }

        if (shouldAlert) {
            AlertsEntity alert = new AlertsEntity();
            alert.setType(setting.getMetric());
            alert.setMessage(setting.getAlertType().name() + " threshold breach");
            alert.setCurrentValue(currentValue);
            alert.setThresholdValue((double) setting.getThresholdValue());
            alert.setTriggeredAt(LocalDateTime.now());
            alert.setAcknowledged(false);

            alertRepository.save(alert);



            System.out.println("Alert generated and sent for: " + setting.getMetric());
        }
    }
}

