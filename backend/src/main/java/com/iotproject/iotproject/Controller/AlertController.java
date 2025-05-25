package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Entity.AlertsEntity;
import com.iotproject.iotproject.Service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts/traffic")
//@CrossOrigin(origins = "*")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @GetMapping("/list")
    public List<AlertsEntity> getAllTrafficAlerts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        List<AlertsEntity> alerts = alertService.getAllAlerts();
        return ResponseEntity.ok(alerts).getBody();
    }

    @PutMapping("/{id}/acknowledge")
    public AlertsEntity acknowledgeAlert(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AlertsEntity alerts  = alertService.acknowledgeAlert(id);
        return ResponseEntity.ok(alerts).getBody();
    }
}
