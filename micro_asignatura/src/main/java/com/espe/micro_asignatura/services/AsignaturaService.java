package com.espe.micro_asignatura.services;

import com.espe.micro_asignatura.models.entities.Asignatura;

import java.util.List;
import java.util.Optional;

public interface AsignaturaService {
    List<Asignatura> findAll();
    Optional<Asignatura> findById(Long id);
    Asignatura save(Asignatura asignatura);
    void deleteById(Long id);

}
