package com.sseprojet.sseprojet.repository;

import com.sseprojet.sseprojet.model.Rapport;
import com.sseprojet.sseprojet.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository pour l'entité Rapport
 */
@Repository
public interface RapportRepository extends JpaRepository<Rapport, Integer> {
    
    /**
     * Trouver les rapports d'un projet
     */
    List<Rapport> findByProjet(Projet projet);
    
    /**
     * Trouver les rapports d'un auteur
     */
    List<Rapport> findByAuteur(String auteur);
    
    /**
     * Trouver les rapports par titre (recherche partielle, insensible à la casse)
     */
    @Query("SELECT r FROM Rapport r WHERE LOWER(r.titre) LIKE LOWER(CONCAT('%', :titre, '%'))")
    List<Rapport> findByTitreContainingIgnoreCase(@Param("titre") String titre);
    
    /**
     * Trouver les rapports entre deux dates
     */
    List<Rapport> findByDateGenerationBetween(LocalDate dateDebut, LocalDate dateFin);
    
    /**
     * Trouver les rapports d'un projet entre deux dates
     */
    List<Rapport> findByProjetAndDateGenerationBetween(Projet projet, LocalDate dateDebut, LocalDate dateFin);
    
    /**
     * Trouver les rapports d'un auteur pour un projet
     */
    List<Rapport> findByAuteurAndProjet(String auteur, Projet projet);
    
    /**
     * Compter les rapports d'un projet
     */
    @Query("SELECT COUNT(r) FROM Rapport r WHERE r.projet = :projet")
    long countByProjet(@Param("projet") Projet projet);
}
