package com.sseprojet.sseprojet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO pour créer et modifier des évaluateurs
 * Évite les problèmes de sérialisation Jackson avec l'héritage
 */
public class EvaluateurDTO {
    
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    
    @Email(message = "L'email doit être valide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasseHash;
    
    private Integer projetId;
    
    // Constructeurs
    public EvaluateurDTO() {}
    
    public EvaluateurDTO(String nom, String email, String motDePasseHash) {
        this.nom = nom;
        this.email = email;
        this.motDePasseHash = motDePasseHash;
    }
    
    public EvaluateurDTO(String nom, String email, String motDePasseHash, Integer projetId) {
        this.nom = nom;
        this.email = email;
        this.motDePasseHash = motDePasseHash;
        this.projetId = projetId;
    }
    
    // Getters et Setters
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMotDePasseHash() {
        return motDePasseHash;
    }
    
    public void setMotDePasseHash(String motDePasseHash) {
        this.motDePasseHash = motDePasseHash;
    }
    
    public Integer getProjetId() {
        return projetId;
    }
    
    public void setProjetId(Integer projetId) {
        this.projetId = projetId;
    }
}
