package com.espe.micro_cursos.services;

import com.espe.micro_cursos.models.entities.Curso;
import com.espe.micro_cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService{
    @Autowired
    private CursoRepository repository;


    @Override
    public List<Curso> findAll() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    public Optional<Curso> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Curso save(Curso curso) {
        return  repository.save(curso);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);

    }
}
