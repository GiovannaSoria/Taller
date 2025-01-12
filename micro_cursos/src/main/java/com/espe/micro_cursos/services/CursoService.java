package com.espe.micro_cursos.services;
import java.util.Optional;
import java.util.List;
import com.espe.micro_cursos.models.entities.Curso;


public interface CursoService {

    List<Curso> findAll();
    Optional<Curso> findById(Long id);
    Curso save(Curso curso);
    void deleteById(Long id);

}