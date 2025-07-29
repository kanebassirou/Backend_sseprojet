package com.sseprojet.sseprojet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Entité représentant une Évaluation de projet
 */
@Entity
@Table(name = "evaluations")
public class Evaluation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull(message = "La date d'évaluation est obligatoire")
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "valeur_mesuree", nullable = false)
    private String valeurMesuree;
    
    @Column(columnDefinition = "TEXT")
    private String commentaire;
    
    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;
    
    // Constructeurs
    public Evaluation() {}
    
    public Evaluation(LocalDate date, String valeurMesuree, String commentaire, Projet projet) {
        this.date = date;
        this.valeurMesuree = valeurMesuree;
        this.commentaire = commentaire;
        this.projet = projet;
    }
    
    // Méthodes métier
    public void saisirEvaluation() {
        // Logique de saisie d'évaluation
    }
    
    public Projet getProjet() {
        return projet;
    }
    
    // Getters et Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getValeurMesuree() {
        return valeurMesuree;
    }
    
    public void setValeurMesuree(String valeurMesuree) {
        this.valeurMesuree = valeurMesuree;
    }
    
    public String getCommentaire() {
        return commentaire;
    }
    
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    
    public void setProjet(Projet projet) {
        this.projet = projet;
    }
    
    @Override
    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", date=" + date +
                ", valeurMesuree='" + valeurMesuree + '\'' +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}
