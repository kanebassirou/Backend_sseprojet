package com.sseprojet.sseprojet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * DTO pour créer et modifier des rapports
 */
public class RapportDTO {
    
    @NotBlank(message = "Le titre du rapport est obligatoire")
    private String titre;
    
    private LocalDate dateGeneration; // Optionnel, sera défini à LocalDate.now() si null
    
    private String contenu;
    
    @NotBlank(message = "L'auteur est obligatoire")
    private String auteur;
    
    @NotNull(message = "L'ID du projet est obligatoire")
    private Integer projetId;
    
    // Constructeurs
    public RapportDTO() {}
    
    public RapportDTO(String titre, LocalDate dateGeneration, String contenu, String auteur, Integer projetId) {
        this.titre = titre;
        this.dateGeneration = dateGeneration;
        this.contenu = contenu;
        this.auteur = auteur;
        this.projetId = projetId;
    }
    
    // Getters et Setters
    public String getTitre() {
        return titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public LocalDate getDateGeneration() {
        return dateGeneration;
    }
    
    public void setDateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
    }
    
    public String getContenu() {
        return contenu;
    }
    
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    
    public String getAuteur() {
        return auteur;
    }
    
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
    
    public Integer getProjetId() {
        return projetId;
    }
    
    public void setProjetId(Integer projetId) {
        this.projetId = projetId;
    }
}
