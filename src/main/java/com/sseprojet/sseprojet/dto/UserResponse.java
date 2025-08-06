package com.sseprojet.sseprojet.dto;

import com.sseprojet.sseprojet.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO pour les réponses utilisateur (sans le mot de passe)
 */
@Schema(description = "Réponse utilisateur sans informations sensibles")
public class UserResponse {
    
    @Schema(description = "Identifiant unique de l'utilisateur", example = "1")
    private Integer id;
    
    @Schema(description = "Nom complet de l'utilisateur", example = "Jean Dupont")
    private String nom;
    
    @Schema(description = "Adresse email de l'utilisateur", example = "jean.dupont@exemple.com")
    private String email;
    
    @Schema(description = "Rôle de l'utilisateur dans le système", example = "ADMINISTRATEUR")
    private String role;
    
    // Constructeur par défaut
    public UserResponse() {}
    
    // Constructeur depuis User
    public UserResponse(User user) {
        this.id = user.getId();
        this.nom = user.getNom();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
    
    // Méthode statique de conversion
    public static UserResponse fromUser(User user) {
        return new UserResponse(user);
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}
