package com.sseprojet.sseprojet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO pour la création d'utilisateurs
 */
@Schema(description = "Données requises pour créer un nouvel utilisateur")
public class CreateUserRequest {
    
    @Schema(description = "Nom complet de l'utilisateur", example = "Jean Dupont", required = true)
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    
    @Schema(description = "Adresse email de l'utilisateur", example = "jean.dupont@exemple.com", required = true)
    @Email(message = "L'email doit être valide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;
    
    @Schema(description = "Mot de passe hashé", example = "password123", required = true)
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasseHash;
    
    @Schema(description = "Type d'utilisateur", example = "ADMINISTRATEUR", required = true,
            allowableValues = {"ADMINISTRATEUR", "CHEF_PROJET", "DECIDEUR", "EVALUATEUR"})
    @NotNull(message = "Le type d'utilisateur est obligatoire")
    private TypeUtilisateur type;
    
    // Constructeurs
    public CreateUserRequest() {}
    
    public CreateUserRequest(String nom, String email, String motDePasseHash, TypeUtilisateur type) {
        this.nom = nom;
        this.email = email;
        this.motDePasseHash = motDePasseHash;
        this.type = type;
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
    
    public TypeUtilisateur getType() {
        return type;
    }
    
    public void setType(TypeUtilisateur type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return "CreateUserRequest{" +
                "nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                '}';
    }
}
