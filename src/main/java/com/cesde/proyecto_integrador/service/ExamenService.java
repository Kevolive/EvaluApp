package com.cesde.proyecto_integrador.service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cesde.proyecto_integrador.model.Examen;
import com.cesde.proyecto_integrador.repository.ExamenRepository;

@Service
public class ExamenService {

    @Autowired
    private ExamenRepository examRepository;

    public List<Examen> findAll() {
        List<Object[]> results = examRepository.findAllBasicData();
        List<Examen> exams = new ArrayList<>();
        for (Object[] result : results) {
            Examen exam = new Examen();
            exam.setId((Long) result[0]);
            exam.setTitulo((String) result[1]);
            exam.setDescripcion((String) result[2]);
            exam.setFechaInicio((LocalDate) result[3]);
            exam.setFechaFin((LocalDate) result[4]);
            exams.add(exam);
        }
        return exams;
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
        examRepository.deleteById(id);
    }
}
