package com.sseprojet.sseprojet.repository;

import com.sseprojet.sseprojet.model.Evaluation;
import com.sseprojet.sseprojet.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository pour l'entité Evaluation
 */
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
    
    List<Evaluation> findByProjet(Projet projet);
    
    List<Evaluation> findByDateBetween(LocalDate dateDebut, LocalDate dateFin);
    
    List<Evaluation> findByProjetAndDateBetween(Projet projet, LocalDate dateDebut, LocalDate dateFin);
}
