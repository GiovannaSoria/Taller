package com.espe.micro_asignatura.controllers;

import com.espe.micro_asignatura.models.entities.Asignatura;
import com.espe.micro_asignatura.services.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaService service;

    // Crear una nueva asignatura
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Asignatura asignatura) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(asignatura));
    }

    // Listar todas las asignaturas
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.findAll());
    }
    // Buscar una asignatura por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Asignatura> asignaturaOptional = service.findById(id);
        if (asignaturaOptional.isPresent()) {
            return ResponseEntity.ok(asignaturaOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    // Modificar una asignatura por su ID
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@RequestBody Asignatura asignatura, @PathVariable Long id) {
        Optional<Asignatura> asignaturaOptional = service.findById(id);
        if (asignaturaOptional.isPresent()) {
            Asignatura asignaturaDB = asignaturaOptional.get();
            asignaturaDB.setNombre(asignatura.getNombre());
            asignaturaDB.setCodigo(asignatura.getCodigo());
            asignaturaDB.setDescripcion(asignatura.getDescripcion());
            asignaturaDB.setCreditos(asignatura.getCreditos());
            asignaturaDB.setNivel(asignatura.getNivel());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(asignaturaDB));
        }
        return ResponseEntity.notFound().build();
    }
    // Eliminar una asignatura por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Asignatura> asignaturaOptional = service.findById(id);
        if (asignaturaOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
