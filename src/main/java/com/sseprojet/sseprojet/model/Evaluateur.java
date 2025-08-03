package com.sseprojet.sseprojet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.*;

/**
 * Entité représentant un Évaluateur
 */
@Entity
@DiscriminatorValue("EVALUATEUR")
@JsonTypeName("EVALUATEUR")
public class Evaluateur extends User {
    
    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_id")
    @JsonIgnoreProperties({"evaluateurs"})
    private Projet projet;
    
    // Constructeurs
    public Evaluateur() {
        super();
    }
    
    public Evaluateur(String nom, String email, String motDePasseHash) {
        super(nom, email, motDePasseHash);
    }
    
    // Méthodes métier
    @Override
    public boolean authentifier() {
        // Logique d'authentification spécifique à l'évaluateur
        return true;
    }
    
    public void saisirEvaluations() {
        // Logique de saisie des évaluations
    }
    
    // Getters et Setters pour la relation
    public Projet getProjet() {
        return projet;
    }
    
    public void setProjet(Projet projet) {
        this.projet = projet;
    }
}
