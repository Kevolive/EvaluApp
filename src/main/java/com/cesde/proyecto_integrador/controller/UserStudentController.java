package com.cesde.proyecto_integrador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.cesde.proyecto_integrador.dto.ProfileDTO;
import com.cesde.proyecto_integrador.service.UserStudentService;

@RestController
@RequestMapping("/api/student")
public class UserStudentController {
    @Autowired
    private UserStudentService userStudentService;

    // Profile
    @GetMapping("/profile/{userId}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userStudentService.getProfile(userId));
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<String> updateProfile(@PathVariable Long userId, @RequestBody ProfileDTO profile) {
        userStudentService.updateProfile(userId, profile);
        return ResponseEntity.ok("Profile updated successfully");
    }
}