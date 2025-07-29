package com.sseprojet.sseprojet.service;

import com.sseprojet.sseprojet.model.Projet;
import com.sseprojet.sseprojet.model.ChefDeProjet;
import com.sseprojet.sseprojet.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des projets
 */
@Service
public class ProjetService {
    
    @Autowired
    private ProjetRepository projetRepository;
    
    public List<Projet> getAllProjets() {
        return projetRepository.findAll();
    }
    
    public Optional<Projet> getProjetById(Integer id) {
        return projetRepository.findById(id);
    }
    
    public List<Projet> getProjetsByChefDeProjet(ChefDeProjet chefDeProjet) {
        return projetRepository.findByChefDeProjet(chefDeProjet);
    }
    
    public List<Projet> getProjetsByStatut(String statut) {
        return projetRepository.findByStatut(statut);
    }
    
    public List<Projet> searchProjetsByTitre(String titre) {
        return projetRepository.findByTitreContaining(titre);
    }
    
    public Projet saveProjet(Projet projet) {
        return projetRepository.save(projet);
    }
    
    public void deleteProjet(Integer id) {
        projetRepository.deleteById(id);
    }
}
