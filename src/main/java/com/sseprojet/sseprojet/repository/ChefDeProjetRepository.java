package com.sseprojet.sseprojet.repository;

import com.sseprojet.sseprojet.model.ChefDeProjet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour l'entité ChefDeProjet
 */
@Repository
public interface ChefDeProjetRepository extends JpaRepository<ChefDeProjet, Integer> {
}
