package com.sseprojet.sseprojet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test", description = "API de test pour vérifier que Swagger fonctionne")
public class TestController {

    @GetMapping("/hello")
    @Operation(summary = "Test simple", description = "Endpoint de test pour vérifier que l'API fonctionne")
    public String hello() {
        return "Hello! L'API fonctionne et Swagger est configuré correctement!";
    }

    @GetMapping("/swagger-status")
    @Operation(summary = "Statut Swagger", description = "Confirme que Swagger est opérationnel")
    public String swaggerStatus() {
        return "Swagger UI est accessible à: http://localhost:8080/swagger-ui.html";
    }
}
