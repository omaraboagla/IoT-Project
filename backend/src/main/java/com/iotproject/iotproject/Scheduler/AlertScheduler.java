// package com.iotproject.iotproject.Scheduler;

// import com.iotproject.iotproject.Entity.AlertsEntity;
// import com.iotproject.iotproject.Entity.Setting;
// import com.iotproject.iotproject.Enum.AlertType;
// import com.iotproject.iotproject.Repo.AlertRepository;
// import com.iotproject.iotproject.Repo.SettingRepository;
// import com.iotproject.iotproject.Repo.StreetLightSensorRepository;
// import com.iotproject.iotproject.Repo.TrafficSensorRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;

// import java.time.LocalDateTime;

// @Component
// public class AlertScheduler {

//     @Autowired
//     private SettingRepository settingRepository;

//     @Autowired
//     private TrafficSensorRepository trafficRepo;

//     @Autowired
//     private AlertRepository alertRepository;

//     @Autowired
//     private StreetLightSensorRepository streetLightSensorRepo;

//     @Value("${SENSOR_SCHEDULER_CRON}")
//     private String sensorSchedulerCron;


//     @Scheduled(cron = "${SENSOR_SCHEDULER_CRON:0 */1 * * * *}")
//     public void evaluateSensorAlerts() {
//         var settings = settingRepository.findAll();
    
//         if (settings.isEmpty()) {
//             System.out.println("No settings found.");
//             return;
//         }
    
//         for (Setting setting : settings) {
//             switch (setting.getType()) {
//                 case Traffic:
//                     trafficRepo.findTopByOrderByTimestampDesc().ifPresent(sensor -> {
//                         Double currentValue = null;
    
//                         switch (setting.getMetric()) {
//                             case "trafficDensity":
//                                 currentValue = Double.valueOf(sensor.getTrafficDensity());
//                                 break;
//                             case "avgSpeed":
//                                 currentValue = sensor.getAvgSpeed();
//                                 break;
//                             default:
//                                 System.out.println("Unknown traffic metric: " + setting.getMetric());
//                         }
    
//                         if (currentValue != null) {
//                             checkAndCreateAlert(setting, currentValue);
//                         }
//                     });
//                     break;
    
//                     case Street_Light:
//                     streetLightSensorRepo.findTopByOrderByTimestampDesc().ifPresent(sensor -> {
//                         Double currentValue = null;
                
//                         switch (setting.getMetric()) {
//                             case "brightnessLevel":
//                                 Integer brightness = sensor.getBrightnessLevel();
//                                 if (brightness != null) {
//                                     currentValue = brightness.doubleValue();
//                                 }
//                                 break;
//                             case "powerConsumption":
//                                 currentValue = sensor.getPowerConsumption();
//                                 break;
//                             default:
//                                 System.out.println("Unknown streetlight metric: " + setting.getMetric());
//                         }
                
//                         if (currentValue != null) {
//                             checkAndCreateAlert(setting, currentValue);
//                         }
//                     });
//                     break;
                
    
//                 default:
//                     System.out.println("Unsupported setting type: " + setting.getType());
//             }
//         }
//     }
    
//     private void checkAndCreateAlert(Setting setting, Double currentValue) {
//         boolean shouldAlert = false;

//         if (setting.getAlertType() == AlertType.above && currentValue > setting.getThresholdValue()) {
//             shouldAlert = true;
//         } else if (setting.getAlertType() == AlertType.below && currentValue < setting.getThresholdValue()) {
//             shouldAlert = true;
//         }

//         if (shouldAlert) {
//             AlertsEntity alert = new AlertsEntity();
//             alert.setType(setting.getMetric());
//             alert.setMessage(setting.getAlertType().name() + " threshold breach");
//             alert.setCurrentValue(currentValue);
//             alert.setThresholdValue((double) setting.getThresholdValue());
//             alert.setTriggeredAt(LocalDateTime.now());
//             alert.setAcknowledged(false);

//             alertRepository.save(alert);



//             System.out.println("Alert generated and sent for: " + setting.getMetric());
//         }
//     }
// }



package com.iotproject.iotproject.Scheduler;

import com.iotproject.iotproject.Entity.*;
import com.iotproject.iotproject.Enum.AlertType;
import com.iotproject.iotproject.Enum.SettingType;
import com.iotproject.iotproject.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AlertScheduler {

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private TrafficSensorRepository trafficSensorRepository;

    @Autowired
    private StreetLightSensorRepository streetLightSensorRepository;

    @Autowired
    private AirPollutionSensorRepository airPollutionSensorRepository;

    @Autowired
    private AlertRepository alertRepository;
    private static final Logger logger = LoggerFactory.getLogger(AlertScheduler.class);


    @Scheduled(cron = "${SENSOR_SCHEDULER_CRON:0 */1 * * * *}")
    public void evaluateSensorAlerts() {
        List<Setting> settings = settingRepository.findAll();
        if (settings.isEmpty()) return;

        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);

        for (Setting setting : settings) {
            switch (setting.getType()) {
                case Traffic -> trafficSensorRepository.findAllByTimestampAfter(oneMinuteAgo).forEach(sensor ->
                        checkAndCreateAlert(setting, setting.getMetric().equals("trafficDensity") ?
                                sensor.getTrafficDensity() : sensor.getAvgSpeed()));
                case Street_Light -> streetLightSensorRepository.findAllByTimestampAfter(oneMinuteAgo).forEach(sensor ->
                        checkAndCreateAlert(setting, setting.getMetric().equals("brightnessLevel") ?
                                sensor.getBrightnessLevel().doubleValue() : sensor.getPowerConsumption()));
                case Air_Pollution -> airPollutionSensorRepository.findAllByTimestampAfter(oneMinuteAgo).forEach(sensor -> {
                    double value = switch (setting.getMetric()) {
                        case "co" -> sensor.getCo();
                        case "ozone" -> sensor.getOzone();
                        default -> -1;
                    };
                    checkAndCreateAlert(setting, value);
                });
            }
        }
    }

    private void checkAndCreateAlert(Setting setting, double currentValue) {
        boolean triggered = switch (setting.getAlertType()) {
            case above -> currentValue > setting.getThresholdValue();
            case below -> currentValue < setting.getThresholdValue();
        };

        if (triggered) {
            AlertsEntity alert = new AlertsEntity();
            alert.setType(setting.getMetric());
            alert.setCurrentValue(currentValue);
            alert.setThresholdValue((double) setting.getThresholdValue());
            alert.setMessage("Threshold breach: " + setting.getAlertType().name());
            alert.setTriggeredAt(LocalDateTime.now());
            alert.setAcknowledged(false);

            alertRepository.save(alert);
            logger.info("🚨 Alert generated for metric [{}]: Current={}, Threshold={}, Type={}",
        setting.getMetric(), currentValue, setting.getThresholdValue(), setting.getAlertType());

        }
    }
}
