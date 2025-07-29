package com.sseprojet.sseprojet.config;

import io.swagger.v3.oas.models.examples.Example;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration des exemples Swagger pour améliorer la documentation API
 */
@Configuration
public class SwaggerExamplesConfig {

    @Bean
    public Example createProjetBasicExample() {
        Example example = new Example();
        example.setSummary("Projet basique");
        example.setDescription("Exemple de création d'un projet avec les champs minimum requis");
        example.setValue("""
            {
                "titre": "Site Web Corporate",
                "dateDebut": "2025-02-01",
                "chefDeProjetId": 3
            }
            """);
        return example;
    }

    @Bean
    public Example createProjetCompletExample() {
        Example example = new Example();
        example.setSummary("Projet complet");
        example.setDescription("Exemple de création d'un projet avec tous les champs remplis");
        example.setValue("""
            {
                "titre": "Application Mobile E-Commerce",
                "description": "Développement d'une application mobile complète pour la vente en ligne avec paiement intégré, gestion des stocks et interface administrateur",
                "dateDebut": "2025-01-15",
                "dateFin": "2025-08-30",
                "budget": 75000,
                "statut": "EN_COURS",
                "chefDeProjetId": 3
            }
            """);
        return example;
    }

    @Bean
    public Example createProjetErpExample() {
        Example example = new Example();
        example.setSummary("Système ERP");
        example.setDescription("Exemple d'un grand projet de système ERP");
        example.setValue("""
            {
                "titre": "Système ERP Entreprise",
                "description": "Implémentation d'un système de planification des ressources d'entreprise complet avec modules RH, Finance, et Production",
                "dateDebut": "2025-03-01",
                "dateFin": "2025-12-31",
                "budget": 150000,
                "statut": "PLANIFIE",
                "chefDeProjetId": 3
            }
            """);
        return example;
    }

    @Bean
    public Example createProjetFormationExample() {
        Example example = new Example();
        example.setSummary("Projet de formation");
        example.setDescription("Exemple d'un projet de formation d'équipe");
        example.setValue("""
            {
                "titre": "Formation Équipe DevOps",
                "description": "Programme de formation intensive sur les pratiques DevOps et CI/CD pour l'équipe de développement",
                "dateDebut": "2025-02-01",
                "dateFin": "2025-03-15",
                "budget": 12000,
                "statut": "PLANIFIE",
                "chefDeProjetId": 3
            }
            """);
        return example;
    }
}
