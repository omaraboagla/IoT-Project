package com.iotproject.iotproject.controller;

import com.iotproject.iotproject.constants.ApiPaths;
import com.iotproject.iotproject.dto.AlertDTO;
import com.iotproject.iotproject.dto.ApiResponseDto;
import com.iotproject.iotproject.entity.AlertsEntity;
import com.iotproject.iotproject.service.AlertService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApiPaths.ALL_ALERTS)
//@CrossOrigin(origins = "*")
public class AlertController {

    @Autowired
    private AlertService alertService;

     @Autowired
    private ModelMapper modelMapper;

    @GetMapping(ApiPaths.LIST_ALERTS)
    public List<AlertsEntity> getAllTrafficAlerts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        List<AlertsEntity> alerts = alertService.getAllAlerts();
        return ResponseEntity.ok(alerts).getBody();
    }

    @PutMapping(ApiPaths.ACKNOWLEDGE_ALERTS)
    public AlertsEntity acknowledgeAlert(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AlertsEntity alerts  = alertService.acknowledgeAlert(id);
        return ResponseEntity.ok(alerts).getBody();
    }

@GetMapping(ApiPaths.ALERT_BY_TYPE)
public ResponseEntity<ApiResponseDto<List<AlertDTO>>> getAlerts(
        @RequestParam(required = false) String type,
        @PageableDefault(size = 10, sort = "triggeredAt", direction = Sort.Direction.DESC) Pageable pageable
) {
    Page<AlertsEntity> alertsPage = (type != null)
            ? alertService.getAlertsByType(type, pageable)
            : alertService.getAllAlerts(pageable);

    List<AlertDTO> dtoList = alertsPage.getContent().stream()
            .map(alert -> modelMapper.map(alert, AlertDTO.class))
            .collect(Collectors.toList());

    Map<String, Object> meta = Map.of(
            "pageNumber", alertsPage.getNumber(),
            "pageSize", alertsPage.getSize(),
            "totalElements", alertsPage.getTotalElements(),
            "totalPages", alertsPage.getTotalPages(),
            "isLast", alertsPage.isLast()
    );

    return ResponseEntity.ok(new ApiResponseDto<>("Success", dtoList, meta));
}


}
