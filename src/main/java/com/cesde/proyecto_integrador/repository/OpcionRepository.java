package com.cesde.proyecto_integrador.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cesde.proyecto_integrador.model.Opcion;


public interface OpcionRepository extends JpaRepository<Opcion, Long> {

    List<Opcion> findByPreguntaId(Long preguntaId);

}
