package com.sseprojet.sseprojet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

/**
 * DTO pour les réponses de projet - évite les références circulaires
 * Fournit une représentation propre et contrôlée des données de projet
 */
@Schema(
    description = "Réponse contenant les informations complètes d'un projet",
    example = """
        {
            "id": 1,
            "titre": "Application Mobile E-Commerce",
            "description": "Développement d'une application mobile complète avec paiement intégré",
            "dateDebut": "2025-01-15",
            "dateFin": "2025-08-30",
            "budget": 75000,
            "statut": "EN_COURS",
            "chefDeProjet": {
                "id": 3,
                "nom": "Marie Chef",
                "email": "marie.chef@exemple.com",
                "role": "CHEF_PROJET"
            }
        }
        """
)
public class ProjetResponse {
    
    @Schema(description = "Identifiant unique du projet", example = "1")
    private Integer id;
    
    @Schema(description = "Titre du projet", example = "Application Mobile E-Commerce")
    private String titre;
    
    @Schema(description = "Description détaillée du projet")
    private String description;
    
    @Schema(description = "Date de début du projet", example = "2025-01-15", type = "string", format = "date")
    private LocalDate dateDebut;
    
    @Schema(description = "Date de fin prévue du projet", example = "2025-08-30", type = "string", format = "date")
    private LocalDate dateFin;
    
    @Schema(description = "Budget alloué au projet en euros", example = "75000")
    private Integer budget;
    
    @Schema(description = "Statut actuel du projet", example = "EN_COURS", 
            allowableValues = {"PLANIFIE", "EN_COURS", "TERMINE", "SUSPENDU"})
    private String statut;
    
    @Schema(description = "Informations résumées du chef de projet responsable")
    private ChefDeProjetSummary chefDeProjet;
    
    // Constructeurs
    public ProjetResponse() {}
    
    public ProjetResponse(Integer id, String titre, String description, LocalDate dateDebut, 
                         LocalDate dateFin, Integer budget, String statut, ChefDeProjetSummary chefDeProjet) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.budget = budget;
        this.statut = statut;
        this.chefDeProjet = chefDeProjet;
    }
    
    // Getters et Setters
    public Integer getId() { 
        return id; 
    }
    
    public void setId(Integer id) { 
        this.id = id; 
    }
    
    public String getTitre() { 
        return titre; 
    }
    
    public void setTitre(String titre) { 
        this.titre = titre; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    public LocalDate getDateDebut() { 
        return dateDebut; 
    }
    
    public void setDateDebut(LocalDate dateDebut) { 
        this.dateDebut = dateDebut; 
    }
    
    public LocalDate getDateFin() { 
        return dateFin; 
    }
    
    public void setDateFin(LocalDate dateFin) { 
        this.dateFin = dateFin; 
    }
    
    public Integer getBudget() { 
        return budget; 
    }
    
    public void setBudget(Integer budget) { 
        this.budget = budget; 
    }
    
    public String getStatut() { 
        return statut; 
    }
    
    public void setStatut(String statut) { 
        this.statut = statut; 
    }
    
    public ChefDeProjetSummary getChefDeProjet() { 
        return chefDeProjet; 
    }
    
    public void setChefDeProjet(ChefDeProjetSummary chefDeProjet) { 
        this.chefDeProjet = chefDeProjet; 
    }
    
    @Override
    public String toString() {
        return "ProjetResponse{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", statut='" + statut + '\'' +
                ", chefDeProjet=" + (chefDeProjet != null ? chefDeProjet.getNom() : "null") +
                '}';
    }
    
    /**
     * Classe interne pour le résumé du chef de projet
     * Évite les références circulaires en ne retournant que les informations essentielles
     */
    @Schema(description = "Informations résumées du chef de projet")
    public static class ChefDeProjetSummary {
        
        @Schema(description = "Identifiant unique du chef de projet", example = "3")
        private Integer id;
        
        @Schema(description = "Nom complet du chef de projet", example = "Marie Chef")
        private String nom;
        
        @Schema(description = "Adresse email du chef de projet", example = "marie.chef@exemple.com")
        private String email;
        
        @Schema(description = "Rôle dans le système", example = "CHEF_PROJET")
        private String role;
        
        // Constructeurs
        public ChefDeProjetSummary() {}
        
        public ChefDeProjetSummary(Integer id, String nom, String email, String role) {
            this.id = id;
            this.nom = nom;
            this.email = email;
            this.role = role;
        }
        
        // Getters et Setters
        public Integer getId() { 
            return id; 
        }
        
        public void setId(Integer id) { 
            this.id = id; 
        }
        
        public String getNom() { 
            return nom; 
        }
        
        public void setNom(String nom) { 
            this.nom = nom; 
        }
        
        public String getEmail() { 
            return email; 
        }
        
        public void setEmail(String email) { 
            this.email = email; 
        }
        
        public String getRole() { 
            return role; 
        }
        
        public void setRole(String role) { 
            this.role = role; 
        }
        
        @Override
        public String toString() {
            return "ChefDeProjetSummary{" +
                    "id=" + id +
                    ", nom='" + nom + '\'' +
                    ", email='" + email + '\'' +
                    ", role='" + role + '\'' +
                    '}';
        }
    }
}
