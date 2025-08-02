package com.sseprojet.sseprojet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un projet
 * Un projet peut avoir plusieurs indicateurs, évaluations, rapports et tâches,
 * et est géré par un ChefDeProjet
 */
@Entity
@Table(name = "projets")
public class Projet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Le titre est obligatoire")
    @Column(nullable = false)
    private String titre;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotNull(message = "La date de début est obligatoire")
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;
    
    @Column(name = "date_fin")
    private LocalDate dateFin;
    
    private Integer budget;
    
    @Column(nullable = false)
    private String statut = "EN_COURS";
    
    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chef_projet_id", nullable = false)
    @JsonIgnoreProperties({"projets"})
    private ChefDeProjet chefDeProjet;
    
    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"projet"})
    private List<Indicateur> indicateurs = new ArrayList<>();
    
    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Evaluation> evaluations = new ArrayList<>();
    
    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rapport> rapports = new ArrayList<>();
    
    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tache> taches = new ArrayList<>();
    
    // Constructeurs
    public Projet() {}
    
    public Projet(String titre, String description, LocalDate dateDebut, Integer budget, ChefDeProjet chefDeProjet) {
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.budget = budget;
        this.chefDeProjet = chefDeProjet;
    }
    
    // Méthodes métier
    public void creerProjet() {
        this.statut = "CREE";
    }
    
    public void mettreAjourProjet() {
        // Logique de mise à jour
    }
    
    public List<Indicateur> getIndicateurs() {
        return indicateurs;
    }
    
    public List<Evaluation> getEvaluations() {
        return evaluations;
    }
    
    public List<Rapport> getRapports() {
        return rapports;
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
    
    public ChefDeProjet getChefDeProjet() {
        return chefDeProjet;
    }
    
    public void setChefDeProjet(ChefDeProjet chefDeProjet) {
        this.chefDeProjet = chefDeProjet;
    }
    
    public void setIndicateurs(List<Indicateur> indicateurs) {
        this.indicateurs = indicateurs;
    }
    
    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }
    
    public void setRapports(List<Rapport> rapports) {
        this.rapports = rapports;
    }
    
    public List<Tache> getTaches() {
        return taches;
    }
    
    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }
    
    @Override
    public String toString() {
        return "Projet{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", statut='" + statut + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
}
