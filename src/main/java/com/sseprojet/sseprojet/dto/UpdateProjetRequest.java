package com.sseprojet.sseprojet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Schema(description = "Requête de mise à jour d'un projet existant")
public class UpdateProjetRequest {
    
    @Schema(description = "Titre du projet", example = "Application Mobile E-Commerce")
    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 3, max = 100, message = "Le titre doit contenir entre 3 et 100 caractères")
    private String titre;
    
    @Schema(description = "Description détaillée du projet")
    @Size(max = 1000, message = "La description ne peut pas dépasser 1000 caractères")
    private String description;
    
    @Schema(description = "Objectifs du projet")
    @Size(max = 500, message = "Les objectifs ne peuvent pas dépasser 500 caractères")
    private String objectifs;
    
    @Schema(description = "Budget du projet en euros", example = "150000")
    @PositiveOrZero(message = "Le budget doit être positif ou zéro")
    private Double budget;
    
    @Schema(description = "Date de début du projet", example = "2025-01-15")
    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;
    
    @Schema(description = "Date de fin prévue du projet", example = "2025-12-31")
    private LocalDate dateFin;
    
    @Schema(description = "Statut du projet", example = "EN_COURS", 
            allowableValues = {"PLANIFIE", "EN_COURS", "TERMINE", "SUSPENDU", "ANNULE"})
    private String statut;
    
    @Schema(description = "ID du chef de projet", example = "1")
    private Integer chefDeProjetId;
    
    // Constructeurs
    public UpdateProjetRequest() {}
    
    // Getters et Setters
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getObjectifs() { return objectifs; }
    public void setObjectifs(String objectifs) { this.objectifs = objectifs; }
    
    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }
    
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    public Integer getChefDeProjetId() { return chefDeProjetId; }
    public void setChefDeProjetId(Integer chefDeProjetId) { this.chefDeProjetId = chefDeProjetId; }
}
