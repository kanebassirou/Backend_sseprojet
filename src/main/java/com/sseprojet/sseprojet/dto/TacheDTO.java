package com.sseprojet.sseprojet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * DTO pour créer et modifier des tâches
 */
public class TacheDTO {
    
    @NotBlank(message = "L'intitulé de la tâche est obligatoire")
    private String intitule;
    
    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;
    
    private LocalDate dateFin;
    
    private String statut = "A_FAIRE"; // Valeur par défaut
    
    @NotNull(message = "L'ID du projet est obligatoire")
    private Integer projetId;
    
    // Constructeurs
    public TacheDTO() {}
    
    public TacheDTO(String intitule, LocalDate dateDebut, LocalDate dateFin, String statut, Integer projetId) {
        this.intitule = intitule;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut != null ? statut : "A_FAIRE";
        this.projetId = projetId;
    }
    
    // Getters et Setters
    public String getIntitule() {
        return intitule;
    }
    
    public void setIntitule(String intitule) {
        this.intitule = intitule;
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
    
    public String getStatut() {
        return statut;
    }
    
    public void setStatut(String statut) {
        this.statut = statut != null ? statut : "A_FAIRE";
    }
    
    public Integer getProjetId() {
        return projetId;
    }
    
    public void setProjetId(Integer projetId) {
        this.projetId = projetId;
    }
}
