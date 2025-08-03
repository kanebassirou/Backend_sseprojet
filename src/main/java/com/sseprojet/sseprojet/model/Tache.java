package com.sseprojet.sseprojet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Entité représentant une Tâche de projet
 */
@Entity
@Table(name = "taches")
public class Tache {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "L'intitulé de la tâche est obligatoire")
    @Column(nullable = false)
    private String intitule;
    
    @NotNull(message = "La date de début est obligatoire")
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;
    
    @Column(name = "date_fin")
    private LocalDate dateFin;
    
    @Column(nullable = false)
    private String statut = "A_FAIRE";
    
    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_id", nullable = false)
    @JsonIgnoreProperties({"taches", "rapports", "indicateurs", "evaluateurs"})
    private Projet projet;
    
    // Constructeurs
    public Tache() {}
    
    public Tache(String intitule, LocalDate dateDebut, LocalDate dateFin, Projet projet) {
        this.intitule = intitule;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.projet = projet;
    }
    
    // Méthodes métier
    public void mettreAJour() {
        // Logique de mise à jour de la tâche
    }
    
    // Getters et Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
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
        this.statut = statut;
    }
    
    public Projet getProjet() {
        return projet;
    }
    
    public void setProjet(Projet projet) {
        this.projet = projet;
    }
    
    @Override
    public String toString() {
        return "Tache{" +
                "id=" + id +
                ", intitule='" + intitule + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", statut='" + statut + '\'' +
                '}';
    }
}
