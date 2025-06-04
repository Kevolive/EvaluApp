package com.cesde.proyecto_integrador.repository;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cesde.proyecto_integrador.model.Opcion;


public interface OpcionRepository extends JpaRepository<Opcion, Long> {

    @Query("SELECT o FROM Opcion o WHERE o.pregunta.id = :preguntaId")
    List<Opcion> findByPreguntaId(@Param("preguntaId") Long preguntaId);

}
