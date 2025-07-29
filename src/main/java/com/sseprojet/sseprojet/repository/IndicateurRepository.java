package com.sseprojet.sseprojet.repository;

import com.sseprojet.sseprojet.model.Indicateur;
import com.sseprojet.sseprojet.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository pour l'entité Indicateur
 */
@Repository
public interface IndicateurRepository extends JpaRepository<Indicateur, Integer> {
    
    List<Indicateur> findByProjet(Projet projet);
    
    List<Indicateur> findByType(String type);
}
