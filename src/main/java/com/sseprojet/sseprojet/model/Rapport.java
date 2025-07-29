package com.sseprojet.sseprojet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Entité représentant un Rapport de projet
 */
@Entity
@Table(name = "rapports")
public class Rapport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Le titre du rapport est obligatoire")
    @Column(nullable = false)
    private String titre;
    
    @NotNull(message = "La date de génération est obligatoire")
    @Column(name = "date_generation", nullable = false)
    private LocalDate dateGeneration;
    
    @Column(columnDefinition = "TEXT")
    private String contenu;
    
    @Column(nullable = false)
    private String auteur;
    
    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;
    
    // Constructeurs
    public Rapport() {}
    
    public Rapport(String titre, LocalDate dateGeneration, String contenu, String auteur, Projet projet) {
        this.titre = titre;
        this.dateGeneration = dateGeneration;
        this.contenu = contenu;
        this.auteur = auteur;
        this.projet = projet;
    }
    
    // Méthodes métier
    public void genererRapport() {
        // Logique de génération de rapport
        this.dateGeneration = LocalDate.now();
    }
    
    public Projet getProjet() {
        return projet;
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
    
    public void setProjet(Projet projet) {
        this.projet = projet;
    }
    
    @Override
    public String toString() {
        return "Rapport{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", dateGeneration=" + dateGeneration +
                ", auteur='" + auteur + '\'' +
                '}';
    }
}
