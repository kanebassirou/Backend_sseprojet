package com.sseprojet.sseprojet.factory;

import com.sseprojet.sseprojet.dto.CreateUserRequest;
import com.sseprojet.sseprojet.model.*;
import org.springframework.stereotype.Component;

/**
 * Factory pour créer les différents types d'utilisateurs
 */
@Component
public class UserFactory {
    
    /**
     * Crée un utilisateur selon le type spécifié
     * @param request Les données de création de l'utilisateur
     * @return L'utilisateur créé
     * @throws IllegalArgumentException Si le type n'est pas reconnu
     */
    public User createUser(CreateUserRequest request) {
        return switch (request.getType()) {
            case ADMINISTRATEUR -> new Administrateur(
                request.getNom(), 
                request.getEmail(), 
                request.getMotDePasseHash()
            );
            case CHEF_PROJET -> new ChefDeProjet(
                request.getNom(), 
                request.getEmail(), 
                request.getMotDePasseHash()
            );
            case DECIDEUR -> new Decideur(
                request.getNom(), 
                request.getEmail(), 
                request.getMotDePasseHash()
            );
            case EVALUATEUR -> new Evaluateur(
                request.getNom(), 
                request.getEmail(), 
                request.getMotDePasseHash()
            );
        };
    }
}
