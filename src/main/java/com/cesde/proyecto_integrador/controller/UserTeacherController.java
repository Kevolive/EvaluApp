package com.cesde.proyecto_integrador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import com.cesde.proyecto_integrador.dto.ProfileDTO;
import com.cesde.proyecto_integrador.service.UserTeacherService;

@RestController
@RequestMapping("/api/teacher")
public class UserTeacherController {
    @Autowired
    private UserTeacherService userTeacherService;

    // Profile
    @GetMapping("/profile/{userId}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userTeacherService.getProfile(userId));
    }

    @GetMapping("/profile")
    public ResponseEntity<List<ProfileDTO>> getAllTeacherProfiles() {
        return ResponseEntity.ok(userTeacherService.getAllTeacherProfiles());
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<String> updateProfile(@PathVariable Long userId, @RequestBody ProfileDTO profile) {
        userTeacherService.updateProfile(userId, profile);
        return ResponseEntity.ok("Profile updated successfully");
    }
}
