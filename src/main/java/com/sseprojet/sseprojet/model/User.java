package com.sseprojet.sseprojet.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Classe abstraite représentant un utilisateur du système
 * Le mot de passe est stocké sous forme de hash
 */
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Administrateur.class, name = "ADMINISTRATEUR"),
    @JsonSubTypes.Type(value = ChefDeProjet.class, name = "CHEF_PROJET"),
    @JsonSubTypes.Type(value = Decideur.class, name = "DECIDEUR"),
    @JsonSubTypes.Type(value = Evaluateur.class, name = "EVALUATEUR")
})
public abstract class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Column(nullable = false)
    private String nom;
    
    @Email(message = "L'email doit être valide")
    @NotBlank(message = "L'email est obligatoire")
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Column(name = "mot_de_passe_hash", nullable = false)
    private String motDePasseHash;
    
    @Column(insertable = false, updatable = false)
    private String role;
    
    // Constructeurs
    public User() {}
    
    public User(String nom, String email, String motDePasseHash) {
        this.nom = nom;
        this.email = email;
        this.motDePasseHash = motDePasseHash;
    }
    
    // Méthodes métier
    public abstract boolean authentifier();
    
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
    
    public String getMotDePasseHash() {
        return motDePasseHash;
    }
    
    public void setMotDePasseHash(String motDePasseHash) {
        this.motDePasseHash = motDePasseHash;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
