package com.sseprojet.sseprojet.model;

import jakarta.persistence.*;

/**
 * Entité représentant un Décideur
 */
@Entity
@DiscriminatorValue("DECIDEUR")
public class Decideur extends User {
    
    // Constructeurs
    public Decideur() {
        super();
    }
    
    public Decideur(String nom, String email, String motDePasseHash) {
        super(nom, email, motDePasseHash);
    }
    
    // Méthodes métier
    @Override
    public boolean authentifier() {
        // Logique d'authentification spécifique au décideur
        return true;
    }
    
    public void consulterRapports() {
        // Logique de consultation des rapports
    }
    
    public void consulterIndicateurs() {
        // Logique de consultation des indicateurs
    }
}
