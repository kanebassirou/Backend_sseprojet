package com.sseprojet.sseprojet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Entité représentant un Indicateur de projet
 */
@Entity
@Table(name = "indicateurs")
public class Indicateur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Le nom de l'indicateur est obligatoire")
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String type;
    
    @Column(columnDefinition = "TEXT")
    private String objectif;
    
    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;
    
    // Constructeurs
    public Indicateur() {}
    
    public Indicateur(String nom, String type, String objectif, Projet projet) {
        this.nom = nom;
        this.type = type;
        this.objectif = objectif;
        this.projet = projet;
    }
    
    // Méthodes métier
    public void saisirIndicateur() {
        // Logique de saisie d'indicateur
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
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getObjectif() {
        return objectif;
    }
    
    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }
    
    public void setProjet(Projet projet) {
        this.projet = projet;
    }
    
    @Override
    public String toString() {
        return "Indicateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", objectif='" + objectif + '\'' +
                '}';
    }
}
