package com.sseprojet.sseprojet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO pour la création de projets
 * Contient toutes les informations nécessaires pour créer un nouveau projet
 */
@Schema(
    description = "Données requises pour créer un nouveau projet dans le système SSE",
    example = """
        {
            "titre": "Application Mobile E-Commerce",
            "description": "Développement d'une application mobile complète avec paiement intégré",
            "dateDebut": "2025-01-15",
            "dateFin": "2025-08-30",
            "budget": 75000,
            "statut": "EN_COURS",
            "chefDeProjetId": 3
        }
        """
)
public class CreateProjetRequest {
    
    @Schema(
        description = "Titre du projet - doit être unique et descriptif",
        example = "Application Mobile E-Commerce",
        required = true,
        minLength = 3,
        maxLength = 100
    )
    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 3, max = 100, message = "Le titre doit contenir entre 3 et 100 caractères")
    private String titre;
    
    @Schema(
        description = "Description détaillée du projet incluant les objectifs et fonctionnalités",
        example = "Développement d'une application mobile complète pour la vente en ligne avec paiement intégré, gestion des stocks et interface administrateur",
        maxLength = 1000
    )
    @Size(max = 1000, message = "La description ne peut pas dépasser 1000 caractères")
    private String description;
    
    @Schema(
        description = "Date de début prévue du projet au format YYYY-MM-DD",
        example = "2025-01-15",
        required = true,
        type = "string",
        format = "date"
    )
    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;
    
    @Schema(
        description = "Date de fin prévue du projet au format YYYY-MM-DD (optionnelle)",
        example = "2025-08-30",
        type = "string",
        format = "date"
    )
    private LocalDate dateFin;
    
    @Schema(
        description = "Budget alloué au projet en euros (nombre entier)",
        example = "75000",
        minimum = "1"
    )
    @Positive(message = "Le budget doit être positif")
    private Integer budget;
    
    @Schema(
        description = "Statut actuel du projet",
        example = "EN_COURS",
        allowableValues = {"PLANIFIE", "EN_COURS", "TERMINE", "SUSPENDU"},
        defaultValue = "PLANIFIE"
    )
    private String statut;
    
    @Schema(
        description = "Identifiant du chef de projet responsable - doit correspondre à un utilisateur de type CHEF_PROJET",
        example = "3",
        required = true,
        minimum = "1"
    )
    @NotNull(message = "L'ID du chef de projet est obligatoire")
    @Positive(message = "L'ID du chef de projet doit être positif")
    private Integer chefDeProjetId;
    
    // Constructeurs
    public CreateProjetRequest() {}
    
    public CreateProjetRequest(String titre, LocalDate dateDebut, Integer chefDeProjetId) {
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.chefDeProjetId = chefDeProjetId;
    }
    
    // Getters et Setters
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
    
    public Integer getChefDeProjetId() {
        return chefDeProjetId;
    }
    
    public void setChefDeProjetId(Integer chefDeProjetId) {
        this.chefDeProjetId = chefDeProjetId;
    }
    
    @Override
    public String toString() {
        return "CreateProjetRequest{" +
                "titre='" + titre + '\'' +
                ", dateDebut=" + dateDebut +
                ", chefDeProjetId=" + chefDeProjetId +
                '}';
    }
}
