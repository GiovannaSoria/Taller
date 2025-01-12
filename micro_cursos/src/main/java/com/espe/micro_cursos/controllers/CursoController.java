package com.espe.micro_cursos.controllers;

import com.espe.micro_cursos.models.entities.Curso;
import com.espe.micro_cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

    // Crear un nuevo curso
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Curso curso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));
    }

    // Listar todos los cursos
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    // Buscar un curso por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Curso> cursoOptional = service.findById(id);
        if (cursoOptional.isPresent()) {
            return ResponseEntity.ok(cursoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Modificar un curso por su ID
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@RequestBody Curso curso, @PathVariable Long id) {
        Optional<Curso> cursoOptional = service.findById(id);
        if (cursoOptional.isPresent()) {
            Curso cursoDB = cursoOptional.get();
            cursoDB.setNombre(curso.getNombre());
            cursoDB.setDescripcion(curso.getDescripcion());
            cursoDB.setCreditos(curso.getCreditos());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDB));
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un curso por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Curso> cursoOptional = service.findById(id);
        if (cursoOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}