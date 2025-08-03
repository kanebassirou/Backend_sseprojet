package com.sseprojet.sseprojet.controller;

import com.sseprojet.sseprojet.dto.EvaluateurDTO;
import com.sseprojet.sseprojet.model.Evaluateur;
import com.sseprojet.sseprojet.model.Projet;
import com.sseprojet.sseprojet.repository.EvaluateurRepository;
import com.sseprojet.sseprojet.service.ProjetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des évaluateurs
 */
@RestController
@RequestMapping("/api/evaluateurs")
@CrossOrigin(origins = "*")
@Tag(name = "Evaluateurs", description = "API de gestion des évaluateurs")
public class EvaluateurController {

    @Autowired
    private EvaluateurRepository evaluateurRepository;

    @Autowired
    private ProjetService projetService;

    /**
     * Récupérer tous les évaluateurs
     */
    @GetMapping
    @Transactional(readOnly = true)
    @Operation(summary = "Lister tous les évaluateurs")
    public ResponseEntity<List<Evaluateur>> getAllEvaluateurs() {
        List<Evaluateur> evaluateurs = evaluateurRepository.findAll();
        return ResponseEntity.ok(evaluateurs);
    }

    /**
     * Récupérer un évaluateur par son ID
     */
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir un évaluateur par ID")
    public ResponseEntity<Evaluateur> getEvaluateurById(@PathVariable Integer id) {
        Optional<Evaluateur> evaluateur = evaluateurRepository.findById(id);
        return evaluateur.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Récupérer un évaluateur par email
     */
    @GetMapping("/email/{email}")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir un évaluateur par email")
    public ResponseEntity<Evaluateur> getEvaluateurByEmail(@PathVariable String email) {
        Optional<Evaluateur> evaluateur = evaluateurRepository.findByEmail(email);
        return evaluateur.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Rechercher des évaluateurs par nom
     */
    @GetMapping("/recherche")
    @Transactional(readOnly = true)
    @Operation(summary = "Rechercher des évaluateurs par nom")
    public ResponseEntity<List<Evaluateur>> rechercherEvaluateurs(@RequestParam String nom) {
        List<Evaluateur> evaluateurs = evaluateurRepository.findByNomContainingIgnoreCase(nom);
        return ResponseEntity.ok(evaluateurs);
    }

    /**
     * Récupérer les évaluateurs d'un projet
     */
    @GetMapping("/projet/{projetId}")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir les évaluateurs d'un projet")
    public ResponseEntity<List<Evaluateur>> getEvaluateursByProjet(@PathVariable Integer projetId) {
        Optional<Projet> projet = projetService.getProjetById(projetId);
        if (projet.isPresent()) {
            List<Evaluateur> evaluateurs = evaluateurRepository.findByProjet(projet.get());
            return ResponseEntity.ok(evaluateurs);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Créer un nouvel évaluateur
     */
    @PostMapping
    @Operation(summary = "Créer un nouvel évaluateur")
    public ResponseEntity<Evaluateur> createEvaluateur(@Valid @RequestBody EvaluateurDTO evaluateurDTO) {
        try {
            // Vérifier que l'email n'existe pas déjà
            if (evaluateurRepository.existsByEmail(evaluateurDTO.getEmail())) {
                return ResponseEntity.badRequest().build();
            }
            
            // Créer l'entité Evaluateur à partir du DTO
            Evaluateur evaluateur = new Evaluateur();
            evaluateur.setNom(evaluateurDTO.getNom());
            evaluateur.setEmail(evaluateurDTO.getEmail());
            evaluateur.setMotDePasseHash(evaluateurDTO.getMotDePasseHash());
            
            // Vérifier que le projet existe si fourni
            if (evaluateurDTO.getProjetId() != null) {
                Optional<Projet> projet = projetService.getProjetById(evaluateurDTO.getProjetId());
                if (projet.isPresent()) {
                    evaluateur.setProjet(projet.get());
                } else {
                    return ResponseEntity.badRequest().build();
                }
            }
            
            Evaluateur savedEvaluateur = evaluateurRepository.save(evaluateur);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvaluateur);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Mettre à jour un évaluateur existant
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un évaluateur")
    public ResponseEntity<Evaluateur> updateEvaluateur(
            @PathVariable Integer id, 
            @Valid @RequestBody EvaluateurDTO evaluateurDTO) {
        
        Optional<Evaluateur> existingEvaluateur = evaluateurRepository.findById(id);
        if (existingEvaluateur.isPresent()) {
            Evaluateur evaluateur = existingEvaluateur.get();
            
            // Vérifier que l'email n'est pas déjà utilisé par un autre utilisateur
            Optional<Evaluateur> emailCheck = evaluateurRepository.findByEmail(evaluateurDTO.getEmail());
            if (emailCheck.isPresent() && !emailCheck.get().getId().equals(id)) {
                return ResponseEntity.badRequest().build();
            }
            
            // Mettre à jour les champs
            evaluateur.setNom(evaluateurDTO.getNom());
            evaluateur.setEmail(evaluateurDTO.getEmail());
            evaluateur.setMotDePasseHash(evaluateurDTO.getMotDePasseHash());
            
            // Vérifier que le projet existe si fourni
            if (evaluateurDTO.getProjetId() != null) {
                Optional<Projet> projet = projetService.getProjetById(evaluateurDTO.getProjetId());
                if (projet.isPresent()) {
                    evaluateur.setProjet(projet.get());
                } else {
                    return ResponseEntity.badRequest().build();
                }
            } else {
                evaluateur.setProjet(null);
            }
            
            Evaluateur updatedEvaluateur = evaluateurRepository.save(evaluateur);
            return ResponseEntity.ok(updatedEvaluateur);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Supprimer un évaluateur
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un évaluateur")
    public ResponseEntity<Void> deleteEvaluateur(@PathVariable Integer id) {
        Optional<Evaluateur> evaluateur = evaluateurRepository.findById(id);
        if (evaluateur.isPresent()) {
            evaluateurRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Obtenir le nombre total d'évaluateurs
     */
    @GetMapping("/count")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir le nombre total d'évaluateurs")
    public ResponseEntity<Long> getEvaluateursCount() {
        long count = evaluateurRepository.count();
        return ResponseEntity.ok(count);
    }

    /**
     * Assigner un évaluateur à un projet
     */
    @PostMapping("/{evaluateurId}/assigner-projet/{projetId}")
    @Operation(summary = "Assigner un évaluateur à un projet")
    public ResponseEntity<Evaluateur> assignerProjet(
            @PathVariable Integer evaluateurId, 
            @PathVariable Integer projetId) {
        
        Optional<Evaluateur> evaluateur = evaluateurRepository.findById(evaluateurId);
        Optional<Projet> projet = projetService.getProjetById(projetId);
        
        if (evaluateur.isPresent() && projet.isPresent()) {
            evaluateur.get().setProjet(projet.get());
            Evaluateur updatedEvaluateur = evaluateurRepository.save(evaluateur.get());
            return ResponseEntity.ok(updatedEvaluateur);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Retirer un évaluateur d'un projet
     */
    @DeleteMapping("/{evaluateurId}/retirer-projet")
    @Operation(summary = "Retirer un évaluateur d'un projet")
    public ResponseEntity<Evaluateur> retirerProjet(@PathVariable Integer evaluateurId) {
        Optional<Evaluateur> evaluateur = evaluateurRepository.findById(evaluateurId);
        if (evaluateur.isPresent()) {
            evaluateur.get().setProjet(null);
            Evaluateur updatedEvaluateur = evaluateurRepository.save(evaluateur.get());
            return ResponseEntity.ok(updatedEvaluateur);
        }
        return ResponseEntity.notFound().build();
    }
}
