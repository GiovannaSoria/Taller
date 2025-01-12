package com.espe.micro_asignatura.services;

import com.espe.micro_asignatura.models.entities.Asignatura;
import com.espe.micro_asignatura.repositories.AsignaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignaturaServiceImpl implements AsignaturaService {

    @Autowired
    private AsignaturaRepository repository;

    @Override
    public List<Asignatura> findAll() {
        return (List<Asignatura>) repository.findAll();
    }

    @Override
    public Optional<Asignatura> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Asignatura save(Asignatura asignatura) {
        return repository.save(asignatura);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
