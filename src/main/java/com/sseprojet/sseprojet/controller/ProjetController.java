package com.sseprojet.sseprojet.controller;

import com.sseprojet.sseprojet.dto.CreateProjetRequest;
import com.sseprojet.sseprojet.dto.ProjetResponse;
import com.sseprojet.sseprojet.dto.UpdateProjetRequest;
import com.sseprojet.sseprojet.model.ChefDeProjet;
import com.sseprojet.sseprojet.model.Projet;
import com.sseprojet.sseprojet.model.User;
import com.sseprojet.sseprojet.service.ProjetService;
import com.sseprojet.sseprojet.service.ProjetMapperService;
import com.sseprojet.sseprojet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import com.sseprojet.sseprojet.security.UserPrincipal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des projets
 * 
 * Ce contrôleur fournit toutes les opérations CRUD pour les projets :
 * - Création avec validation des données
 * - Lecture avec filtres et recherche
 * - Mise à jour complète
 * - Suppression sécurisée
 */
@RestController
@RequestMapping("/api/projets")
@CrossOrigin(origins = "*")
@Tag(
    name = "Projets", 
    description = """
        API de gestion des projets du système SSE.
        
        Permet de gérer le cycle de vie complet des projets :
        création, suivi, évaluation et clôture.
        """
)
public class ProjetController {
    
    @Autowired
    private ProjetService projetService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProjetMapperService projetMapperService;
    
    @Operation(
        summary = "Récupérer tous les projets",
        description = "Retourne la liste complète de tous les projets du système avec leurs informations détaillées"
    )
    @ApiResponse(
        responseCode = "200", 
        description = "Liste des projets récupérée avec succès",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ProjetResponse.class)
        )
    )
    @GetMapping
    public ResponseEntity<List<ProjetResponse>> getAllProjets() {
        List<Projet> projets = projetService.getAllProjets();
        List<ProjetResponse> responses = projetMapperService.convertToResponseList(projets);
        return ResponseEntity.ok(responses);
    }
    
    @Operation(
        summary = "Récupérer un projet par ID",
        description = "Retourne les détails complets d'un projet spécifique basé sur son identifiant unique"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Projet trouvé et retourné avec succès",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ProjetResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Projet non trouvé avec l'ID spécifié"
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjetResponse> getProjetById(
            @Parameter(
                description = "Identifiant unique du projet à récupérer", 
                example = "1",
                required = true
            )
            @PathVariable Integer id) {
        Optional<Projet> projet = projetService.getProjetById(id);
        if (projet.isPresent()) {
            ProjetResponse response = projetMapperService.convertToResponse(projet.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(
        summary = "Récupérer des projets par statut",
        description = "Filtre et retourne tous les projets ayant le statut spécifié"
    )
    @ApiResponse(
        responseCode = "200", 
        description = "Liste des projets avec le statut spécifié récupérée avec succès",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ProjetResponse.class)
        )
    )
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<ProjetResponse>> getProjetsByStatut(
            @Parameter(
                description = "Statut des projets à récupérer",
                example = "EN_COURS",
                schema = @Schema(allowableValues = {"PLANIFIE", "EN_COURS", "TERMINE", "SUSPENDU"})
            )
            @PathVariable String statut) {
        List<Projet> projets = projetService.getProjetsByStatut(statut);
        List<ProjetResponse> responses = projetMapperService.convertToResponseList(projets);
        return ResponseEntity.ok(responses);
    }
    
    @Operation(
        summary = "Rechercher des projets par titre",
        description = "Effectue une recherche textuelle dans les titres de projets (recherche partielle insensible à la casse)"
    )
    @ApiResponse(
        responseCode = "200", 
        description = "Liste des projets correspondants à la recherche récupérée avec succès",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ProjetResponse.class)
        )
    )
    @GetMapping("/search")
    public ResponseEntity<List<ProjetResponse>> searchProjets(
            @Parameter(
                description = "Terme de recherche pour filtrer les projets par titre",
                example = "Mobile",
                required = true
            )
            @RequestParam String titre) {
        List<Projet> projets = projetService.searchProjetsByTitre(titre);
        List<ProjetResponse> responses = projetMapperService.convertToResponseList(projets);
        return ResponseEntity.ok(responses);
    }
    
    @Operation(
        summary = "Créer un nouveau projet",
        description = """
            Crée un nouveau projet dans le système SSE avec les informations fournies.
            
            **Étapes de création :**
            1. Validation des données d'entrée
            2. Vérification de l'existence du chef de projet
            3. Création du projet avec statut par défaut 'PLANIFIE' si non spécifié
            4. Sauvegarde en base de données
            
            **Note :** Le chef de projet doit exister et avoir le rôle CHEF_PROJET.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Projet créé avec succès",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ProjetResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Données invalides - erreurs de validation ou chef de projet incorrect",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Chef de projet non trouvé avec l'ID spécifié",
            content = @Content(mediaType = "application/json")
        )
    })
    @PostMapping
    public ResponseEntity<ProjetResponse> createProjet(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = """
                    Données complètes pour la création d'un projet.
                    
                    **Champs obligatoires :**
                    - titre : Nom du projet (3-100 caractères)
                    - dateDebut : Date de début au format YYYY-MM-DD
                    - chefDeProjetId : ID d'un utilisateur avec le rôle CHEF_PROJET
                    
                    **Champs optionnels :**
                    - description : Description détaillée (max 1000 caractères)
                    - dateFin : Date de fin prévue
                    - budget : Budget en euros (nombre entier positif)
                    - statut : PLANIFIE (défaut), EN_COURS, TERMINE, SUSPENDU
                    """,
                required = true,
                content = @Content(schema = @Schema(implementation = CreateProjetRequest.class))
            )
            @Valid @RequestBody CreateProjetRequest request) {
        try {
            // Récupérer le chef de projet
            Optional<User> chefOpt = userService.getUserById(request.getChefDeProjetId());
            if (chefOpt.isEmpty() || !(chefOpt.get() instanceof ChefDeProjet)) {
                return ResponseEntity.badRequest().build();
            }
            
            // Créer le projet
            Projet projet = new Projet();
            projet.setTitre(request.getTitre());
            projet.setDescription(request.getDescription());
            projet.setDateDebut(request.getDateDebut());
            projet.setDateFin(request.getDateFin());
            projet.setBudget(request.getBudget() != null ? request.getBudget().intValue() : null);
            projet.setStatut(request.getStatut() != null ? request.getStatut() : "PLANIFIE");
            projet.setChefDeProjet((ChefDeProjet) chefOpt.get());
            
            Projet savedProjet = projetService.saveProjet(projet);
            ProjetResponse response = projetMapperService.convertToResponse(savedProjet);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(
        summary = "Mettre à jour un projet existant",
        description = "Met à jour les informations d'un projet existant avec de nouvelles données"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Projet mis à jour avec succès",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ProjetResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Projet non trouvé avec l'ID spécifié"
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProjetResponse> updateProjet(
            @Parameter(description = "ID du projet à mettre à jour", example = "1", required = true)
            @PathVariable Integer id, 
            @Valid @RequestBody UpdateProjetRequest request,
            Authentication auth) {
        
        try {
            System.out.println("🚀 PUT /api/projets/" + id + " appelé par: " + auth.getName());
            
            // Vérifier que le projet existe
            Optional<Projet> existingProjetOpt = projetService.getProjetById(id);
            if (existingProjetOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Projet existingProjet = existingProjetOpt.get();
            
            // Mettre à jour les champs
            existingProjet.setTitre(request.getTitre());
            existingProjet.setDescription(request.getDescription());
            existingProjet.setDateDebut(request.getDateDebut());
            existingProjet.setDateFin(request.getDateFin());
            existingProjet.setBudget(request.getBudget() != null ? request.getBudget().intValue() : null);
            
            if (request.getStatut() != null) {
                existingProjet.setStatut(request.getStatut());
            }
            
            // Mettre à jour le chef de projet si fourni
            if (request.getChefDeProjetId() != null) {
                Optional<User> chefOpt = userService.getUserById(request.getChefDeProjetId());
                if (chefOpt.isPresent() && chefOpt.get() instanceof ChefDeProjet) {
                    existingProjet.setChefDeProjet((ChefDeProjet) chefOpt.get());
                }
            }
            
            // Sauvegarder
            Projet updatedProjet = projetService.saveProjet(existingProjet);
            ProjetResponse response = projetMapperService.convertToResponse(updatedProjet);
            
            System.out.println("✅ Projet mis à jour avec ID: " + updatedProjet.getId());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la mise à jour: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(
        summary = "Supprimer un projet",
        description = "Supprime définitivement un projet du système"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204", 
            description = "Projet supprimé avec succès"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Projet non trouvé avec l'ID spécifié"
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjet(
            @Parameter(description = "ID du projet à supprimer", example = "1", required = true)
            @PathVariable Integer id) {
        Optional<Projet> projet = projetService.getProjetById(id);
        if (projet.isPresent()) {
            projetService.deleteProjet(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Ajoutez cette méthode temporaire pour le debug
    @GetMapping("/test-permissions")
    @Operation(summary = "Tester les permissions utilisateur")
    public ResponseEntity<Map<String, Object>> testPermissions(Authentication auth) {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        
        Map<String, Object> response = new HashMap<>();
        response.put("email", userPrincipal.getEmail());
        response.put("role", userPrincipal.getRole());
        response.put("authorities", userPrincipal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));
        response.put("canCreateProject", userPrincipal.getAuthorities().stream()
            .anyMatch(auth2 -> auth2.getAuthority().equals("ROLE_CHEF_PROJET") || 
                              auth2.getAuthority().equals("ROLE_ADMINISTRATEUR")));
        
        return ResponseEntity.ok(response);
    }
}
