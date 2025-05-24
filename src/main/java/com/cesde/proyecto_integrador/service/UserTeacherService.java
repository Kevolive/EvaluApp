package com.cesde.proyecto_integrador.service;

import java.util.List;

import com.cesde.proyecto_integrador.dto.ProfileDTO;


public interface UserTeacherService {
    ProfileDTO getProfile(Long userId);
    void updateProfile(Long userId, ProfileDTO profile);
    List<ProfileDTO> getAllTeacherProfiles();
}
