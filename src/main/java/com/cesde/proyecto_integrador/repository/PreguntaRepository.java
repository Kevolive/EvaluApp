package com.cesde.proyecto_integrador.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cesde.proyecto_integrador.model.Pregunta;
import java.util.List;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    List<Pregunta> findByExamenId(Long examenId);
}
