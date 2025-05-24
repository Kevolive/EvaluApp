package com.cesde.proyecto_integrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cesde.proyecto_integrador.model.Examen;
import java.util.List;

public interface ExamenRepository extends JpaRepository<Examen, Long> {
    @Query("SELECT e.id, e.titulo, e.descripcion, e.fechaInicio, e.fechaFin FROM Examen e")
    List<Object[]> findAllBasicData();
}
