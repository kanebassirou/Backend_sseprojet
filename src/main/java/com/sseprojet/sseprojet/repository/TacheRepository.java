package com.sseprojet.sseprojet.repository;

import com.sseprojet.sseprojet.model.Tache;
import com.sseprojet.sseprojet.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository pour l'entité Tache
 */
@Repository
public interface TacheRepository extends JpaRepository<Tache, Integer> {
    
    /**
     * Trouver les tâches d'un projet
     */
    List<Tache> findByProjet(Projet projet);
    
    /**
     * Trouver les tâches par statut
     */
    List<Tache> findByStatut(String statut);
    
    /**
     * Trouver les tâches d'un projet par statut
     */
    List<Tache> findByProjetAndStatut(Projet projet, String statut);
    
    /**
     * Rechercher des tâches par intitulé (recherche partielle, insensible à la casse)
     */
    @Query("SELECT t FROM Tache t WHERE LOWER(t.intitule) LIKE LOWER(CONCAT('%', :intitule, '%'))")
    List<Tache> findByIntituleContainingIgnoreCase(@Param("intitule") String intitule);
    
    /**
     * Trouver les tâches qui se terminent entre deux dates
     */
    List<Tache> findByDateFinBetween(LocalDate dateDebut, LocalDate dateFin);
    
    /**
     * Trouver les tâches en retard (date de fin dépassée et statut != TERMINEE)
     */
    @Query("SELECT t FROM Tache t WHERE t.dateFin < :dateActuelle AND t.statut != 'TERMINEE'")
    List<Tache> findTachesEnRetard(@Param("dateActuelle") LocalDate dateActuelle);
    
    /**
     * Trouver les tâches d'un projet en retard
     */
    @Query("SELECT t FROM Tache t WHERE t.projet = :projet AND t.dateFin < :dateActuelle AND t.statut != 'TERMINEE'")
    List<Tache> findTachesEnRetardByProjet(@Param("projet") Projet projet, @Param("dateActuelle") LocalDate dateActuelle);
    
    /**
     * Compter les tâches par statut pour un projet
     */
    @Query("SELECT COUNT(t) FROM Tache t WHERE t.projet = :projet AND t.statut = :statut")
    long countByProjetAndStatut(@Param("projet") Projet projet, @Param("statut") String statut);
    
    /**
     * Compter le total des tâches d'un projet
     */
    @Query("SELECT COUNT(t) FROM Tache t WHERE t.projet = :projet")
    long countByProjet(@Param("projet") Projet projet);
}
