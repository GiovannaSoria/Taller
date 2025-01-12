package com.espe.micro_asignatura.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class HomeController {

    // Opción A: Redirigir automáticamente a /api/asignaturas
    @GetMapping("/")
    public ResponseEntity<Void> redirectRoot() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/api/asignaturas"))
                .build();
    }

    /*
    // Opción B: Mostrar un mensaje fijo en la raíz
    @GetMapping("/")
    public String homeMessage() {
        return "¡Bienvenido al microservicio de asignaturas!";
    }
    */
}