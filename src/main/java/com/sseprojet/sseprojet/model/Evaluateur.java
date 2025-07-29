package com.sseprojet.sseprojet.model;

import jakarta.persistence.*;

/**
 * Entité représentant un Évaluateur
 */
@Entity
@DiscriminatorValue("EVALUATEUR")
public class Evaluateur extends User {
    
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
}
