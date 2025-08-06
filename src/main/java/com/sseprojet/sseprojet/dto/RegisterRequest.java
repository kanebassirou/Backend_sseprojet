package com.sseprojet.sseprojet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Données d'inscription d'un nouvel utilisateur")
public class RegisterRequest {
    
    @Schema(description = "Nom complet de l'utilisateur", example = "Jean Dupont")
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;
    
    @Schema(description = "Email de l'utilisateur", example = "jean.dupont@exemple.com")
    @Email(message = "L'email doit être valide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;
    
    @Schema(description = "Mot de passe", example = "motdepasse123")
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, max = 50, message = "Le mot de passe doit contenir entre 6 et 50 caractères")
    private String motDePasse;
    
    @Schema(description = "Type d'utilisateur", example = "CHEF_PROJET", 
            allowableValues = {"ADMINISTRATEUR", "CHEF_PROJET", "DECIDEUR", "EVALUATEUR"})
    @NotBlank(message = "Le type d'utilisateur est obligatoire")
    @Pattern(regexp = "ADMINISTRATEUR|CHEF_PROJET|DECIDEUR|EVALUATEUR", 
             message = "Type d'utilisateur invalide. Valeurs autorisées : ADMINISTRATEUR, CHEF_PROJET, DECIDEUR, EVALUATEUR")
    private String typeUtilisateur;
    
    // Constructeurs
    public RegisterRequest() {}
    
    public RegisterRequest(String nom, String email, String motDePasse, String typeUtilisateur) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.typeUtilisateur = typeUtilisateur;
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
    
    public String getMotDePasse() { 
        return motDePasse; 
    }
    
    public void setMotDePasse(String motDePasse) { 
        this.motDePasse = motDePasse; 
    }
    
    public String getTypeUtilisateur() { 
        return typeUtilisateur; 
    }
    
    public void setTypeUtilisateur(String typeUtilisateur) { 
        this.typeUtilisateur = typeUtilisateur; 
    }
}
