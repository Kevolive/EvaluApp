package com.cesde.proyecto_integrador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cesde.proyecto_integrador.model.Examen;
import com.cesde.proyecto_integrador.repository.ExamenRepository;

@Service
public class ExamenService {

    @Autowired
    private ExamenRepository examRepository;

    public List<Examen> findAll() {
        return examRepository.findAll();
    }

    public Examen findById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Examen no encontrado con id: " + id));
    }

    public Examen save(Examen exam) {
        return examRepository.save(exam);
    }

    public Examen update(Long id, Examen examDetails) {
        Examen exam = examRepository.findById(id).orElseThrow(() -> new RuntimeException("Exam not found"));
        exam.setTitulo(examDetails.getTitulo());
        exam.setDescripcion(examDetails.getDescripcion());
        return examRepository.save(exam);
    }

    public void delete(Long id) {
        Examen examen = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Examen no encontrado con id: " + id));

        if (examen.getPreguntas() != null && !examen.getPreguntas().isEmpty()) {
            examen.getPreguntas().clear();
        }

        examRepository.save(examen);

        examRepository.delete(examen);
    }
}
