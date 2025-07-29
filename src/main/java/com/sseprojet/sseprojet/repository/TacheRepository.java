package com.sseprojet.sseprojet.repository;

import com.sseprojet.sseprojet.model.Tache;
import com.sseprojet.sseprojet.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository pour l'entité Tache
 */
@Repository
public interface TacheRepository extends JpaRepository<Tache, Integer> {
    
    List<Tache> findByProjet(Projet projet);
    
    List<Tache> findByStatut(String statut);
    
    List<Tache> findByProjetAndStatut(Projet projet, String statut);
}
