package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Dto.PasswordUpdateDto;
import com.iotproject.iotproject.Dto.ResponseDto;
import com.iotproject.iotproject.Dto.UserProfileDto;
import com.iotproject.iotproject.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ResponseEntity<UserProfileDto> getProfile() {
        return ResponseEntity.ok(profileService.getCurrentUserProfile());
    }

    @PutMapping("/password")
    public ResponseEntity<ResponseDto> updatePassword(@RequestBody PasswordUpdateDto dto) {
       ResponseDto response =  profileService.updatePassword(dto);
        return ResponseEntity.ok(response);
    }
}
