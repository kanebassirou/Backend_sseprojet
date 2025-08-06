package com.sseprojet.sseprojet.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse d'authentification avec token JWT")
public class JwtResponse {
    
    @Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    
    @Schema(description = "Type de token", example = "Bearer")
    private String type = "Bearer";
    
    @Schema(description = "ID de l'utilisateur", example = "1")
    private Integer id;
    
    @Schema(description = "Email de l'utilisateur", example = "admin@exemple.com")
    private String email;
    
    @Schema(description = "Nom de l'utilisateur", example = "Admin User")
    private String nom;
    
    @Schema(description = "Rôle de l'utilisateur", example = "ADMINISTRATEUR")
    private String role;
    
    // Constructeurs
    public JwtResponse() {}
    
    public JwtResponse(String token, Integer id, String email, String nom, String role) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.role = role;
    }
    
    // Getters et Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
