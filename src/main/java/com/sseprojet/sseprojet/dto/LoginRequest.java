package com.sseprojet.sseprojet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Données de connexion")
public class LoginRequest {
    
    @Schema(description = "Email de l'utilisateur", example = "admin@exemple.com")
    @Email(message = "L'email doit être valide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;
    
    @Schema(description = "Mot de passe", example = "motdepasse123")
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;
    
    // Constructeurs
    public LoginRequest() {}
    
    public LoginRequest(String email, String motDePasse) {
        this.email = email;
        this.motDePasse = motDePasse;
    }
    
    // Getters et Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
}
