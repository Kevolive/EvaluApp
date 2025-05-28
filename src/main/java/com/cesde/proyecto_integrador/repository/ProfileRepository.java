package com.cesde.proyecto_integrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cesde.proyecto_integrador.model.Profile;


public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
