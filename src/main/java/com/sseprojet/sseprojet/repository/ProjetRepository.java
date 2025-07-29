package com.sseprojet.sseprojet.repository;

import com.sseprojet.sseprojet.model.Projet;
import com.sseprojet.sseprojet.model.ChefDeProjet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository pour l'entité Projet
 */
@Repository
public interface ProjetRepository extends JpaRepository<Projet, Integer> {
    
    List<Projet> findByChefDeProjet(ChefDeProjet chefDeProjet);
    
    List<Projet> findByStatut(String statut);
    
    @Query("SELECT p FROM Projet p WHERE p.titre LIKE %:titre%")
    List<Projet> findByTitreContaining(@Param("titre") String titre);
}
