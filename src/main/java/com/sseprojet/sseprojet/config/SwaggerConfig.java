package com.sseprojet.sseprojet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration Swagger/OpenAPI pour la documentation de l'API
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API SSE Projet - Système de Suivi et d'Évaluation")
                        .version("1.0.0")
                        .description("API REST pour la gestion de projets, utilisateurs, indicateurs, évaluations et rapports")
                        .contact(new Contact()
                                .name("Bassirou Kane")
                                .email("bassirou.kane@exemple.com")
                                .url("https://github.com/bassirou-kane"))
                        .license(new License()
                                .name("Licence Libre")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Serveur de développement local"),
                        new Server()
                                .url("https://api.sseprojet.com")
                                .description("Serveur de production")
                ));
    }
}
