package com.sseprojet.sseprojet.service;

import com.sseprojet.sseprojet.dto.ProjetResponse;
import com.sseprojet.sseprojet.model.ChefDeProjet;
import com.sseprojet.sseprojet.model.Projet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de conversion (Mapper) pour transformer les entités en DTOs
 * Centralise la logique de conversion et évite la duplication de code
 */
@Service
public class ProjetMapperService {
    
    /**
     * Convertit une entité Projet en DTO ProjetResponse
     * @param projet L'entité Projet à convertir
     * @return Le DTO ProjetResponse correspondant
     */
    public ProjetResponse convertToResponse(Projet projet) {
        if (projet == null) {
            return null;
        }
        
        ProjetResponse response = new ProjetResponse();
        response.setId(projet.getId());
        response.setTitre(projet.getTitre());
        response.setDescription(projet.getDescription());
        response.setDateDebut(projet.getDateDebut());
        response.setDateFin(projet.getDateFin());
        response.setBudget(projet.getBudget());
        response.setStatut(projet.getStatut());
        
        // Conversion du chef de projet en résumé
        ChefDeProjet chef = projet.getChefDeProjet();
        if (chef != null) {
            ProjetResponse.ChefDeProjetSummary chefSummary = new ProjetResponse.ChefDeProjetSummary(
                chef.getId(),
                chef.getNom(),
                chef.getEmail(),
                chef.getRole()
            );
            response.setChefDeProjet(chefSummary);
        }
        
        return response;
    }
    
    /**
     * Convertit une liste d'entités Projet en liste de DTOs ProjetResponse
     * @param projets La liste d'entités Projet à convertir
     * @return La liste de DTOs ProjetResponse correspondante
     */
    public List<ProjetResponse> convertToResponseList(List<Projet> projets) {
        if (projets == null) {
            return null;
        }
        
        return projets.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Convertit un ChefDeProjet en résumé pour éviter les références circulaires
     * @param chef L'entité ChefDeProjet à convertir
     * @return Le résumé du chef de projet
     */
    public ProjetResponse.ChefDeProjetSummary convertChefToSummary(ChefDeProjet chef) {
        if (chef == null) {
            return null;
        }
        
        return new ProjetResponse.ChefDeProjetSummary(
            chef.getId(),
            chef.getNom(),
            chef.getEmail(),
            chef.getRole()
        );
    }
}
