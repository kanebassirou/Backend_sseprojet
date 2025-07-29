package com.sseprojet.sseprojet.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un Chef de Projet
 * Un ChefDeProjet peut gérer plusieurs projets
 */
@Entity
@DiscriminatorValue("CHEF_PROJET")
public class ChefDeProjet extends User {
    
    // Relations
    @OneToMany(mappedBy = "chefDeProjet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Projet> projets = new ArrayList<>();
    
    // Constructeurs
    public ChefDeProjet() {
        super();
    }
    
    public ChefDeProjet(String nom, String email, String motDePasseHash) {
        super(nom, email, motDePasseHash);
    }
    
    // Méthodes métier
    @Override
    public boolean authentifier() {
        // Logique d'authentification spécifique au chef de projet
        return true;
    }
    
    public void creerProjet() {
        // Logique de création de projet
    }
    
    public void mettreAjourProjet() {
        // Logique de mise à jour de projet
    }
    
    public void suivreIndicateurs() {
        // Logique de suivi des indicateurs
    }
    
    // Getters et Setters
    public List<Projet> getProjets() {
        return projets;
    }
    
    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }
    
    // Méthode pour ajouter un projet
    public void ajouterProjet(Projet projet) {
        projets.add(projet);
        projet.setChefDeProjet(this);
    }
    
    // Méthode pour retirer un projet
    public void retirerProjet(Projet projet) {
        projets.remove(projet);
        projet.setChefDeProjet(null);
    }
}
