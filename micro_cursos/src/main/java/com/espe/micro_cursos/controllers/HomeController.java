package com.espe.micro_cursos.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Void> rootRedirect() {
        // Ejemplo: redirigir a /api/cursos
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/api/cursos"))
                .build();
    }

    // O si quieres solo mostrar un texto:
    // @GetMapping("/")
    // public String rootMessage() {
    //     return "¡Bienvenido al microservicio de cursos!";
    // }
}
