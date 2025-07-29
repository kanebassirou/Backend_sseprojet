package com.sseprojet.sseprojet.model;

import jakarta.persistence.*;

/**
 * Entité représentant un Administrateur du système
 */
@Entity
@DiscriminatorValue("ADMINISTRATEUR")
public class Administrateur extends User {
    
    // Constructeurs
    public Administrateur() {
        super();
    }
    
    public Administrateur(String nom, String email, String motDePasseHash) {
        super(nom, email, motDePasseHash);
    }
    
    // Méthodes métier
    @Override
    public boolean authentifier() {
        // Logique d'authentification spécifique à l'administrateur
        return true;
    }
    
    public void gererUtilisateurs() {
        // Logique de gestion des utilisateurs
    }
    
    public void gererRoles() {
        // Logique de gestion des rôles
    }
}
