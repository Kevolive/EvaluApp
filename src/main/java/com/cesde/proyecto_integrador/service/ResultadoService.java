package com.cesde.proyecto_integrador.service;

import com.cesde.proyecto_integrador.model.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cesde.proyecto_integrador.dto.ResultadoRequestDTO;
import com.cesde.proyecto_integrador.model.Examen;
import com.cesde.proyecto_integrador.model.Resultado;
import com.cesde.proyecto_integrador.repository.ExamenRepository;
import com.cesde.proyecto_integrador.repository.ResultadoRepository;
import com.cesde.proyecto_integrador.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResultadoService {

    @Autowired
    private ResultadoRepository resultadoRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamenRepository examenRepository;

    public Resultado save(ResultadoRequestDTO resultadoDTO) {
        User estudiante = userRepository.findById(resultadoDTO.getEstudianteId())
            .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));

        Examen examen = examenRepository.findById(resultadoDTO.getExamenId())
            .orElseThrow(() -> new EntityNotFoundException("Examen no encontrado"));

        Resultado resultado = new Resultado();
        resultado.setPuntuacion(resultadoDTO.getPuntuacion());
        resultado.setFechaFinalizacion(resultadoDTO.getFechaFinalizacion());
        resultado.setEstudiante(estudiante);
        resultado.setExamen(examen);

        return resultadoRepository.save(resultado);
    }

    public List<Resultado> findAll() {
        return resultadoRepository.findAll();
    }

    public Resultado findById(Long id) {
        return resultadoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Resultado no encontrado"));
    }

    public List<Resultado> findByEstudianteId(Long estudianteId) {
        User estudiante = userRepository.findById(estudianteId)
            .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));
        return resultadoRepository.findByEstudiante(estudiante);
    }

    public void delete(Long id) {
        if (!resultadoRepository.existsById(id)) {
            throw new EntityNotFoundException("Resultado no encontrado");
        }
        resultadoRepository.deleteById(id);}
}

