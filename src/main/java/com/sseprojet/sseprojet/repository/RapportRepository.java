package com.sseprojet.sseprojet.repository;

import com.sseprojet.sseprojet.model.Rapport;
import com.sseprojet.sseprojet.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository pour l'entité Rapport
 */
@Repository
public interface RapportRepository extends JpaRepository<Rapport, Integer> {
    
    List<Rapport> findByProjet(Projet projet);
    
    List<Rapport> findByAuteur(String auteur);
}
