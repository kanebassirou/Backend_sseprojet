package com.sseprojet.sseprojet.repository;

import com.sseprojet.sseprojet.model.Evaluateur;
import com.sseprojet.sseprojet.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des Evaluateurs
 */
@Repository
public interface EvaluateurRepository extends JpaRepository<Evaluateur, Integer> {
    
    /**
     * Trouver un évaluateur par email
     */
    Optional<Evaluateur> findByEmail(String email);
    
    /**
     * Trouver les évaluateurs par nom (recherche partielle, insensible à la casse)
     */
    @Query("SELECT e FROM Evaluateur e WHERE LOWER(e.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Evaluateur> findByNomContainingIgnoreCase(@Param("nom") String nom);
    
    /**
     * Trouver les évaluateurs d'un projet
     */
    List<Evaluateur> findByProjet(Projet projet);
    
    /**
     * Vérifier si un email existe déjà
     */
    boolean existsByEmail(String email);
    
    /**
     * Compter le nombre total d'évaluateurs
     */
    @Query("SELECT COUNT(e) FROM Evaluateur e")
    long countEvaluateurs();
    
    /**
     * Compter les évaluateurs d'un projet
     */
    @Query("SELECT COUNT(e) FROM Evaluateur e WHERE e.projet = :projet")
    long countByProjet(@Param("projet") Projet projet);
}
