package com.sseprojet.sseprojet.controller;

import com.sseprojet.sseprojet.dto.TacheDTO;
import com.sseprojet.sseprojet.model.Tache;
import com.sseprojet.sseprojet.model.Projet;
import com.sseprojet.sseprojet.repository.TacheRepository;
import com.sseprojet.sseprojet.service.ProjetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des tâches
 */
@RestController
@RequestMapping("/api/taches")
@CrossOrigin(origins = "*")
@Tag(name = "Taches", description = "API de gestion des tâches de projet")
public class TacheController {

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private ProjetService projetService;

    /**
     * Récupérer toutes les tâches
     */
    @GetMapping
    @Transactional(readOnly = true)
    @Operation(summary = "Lister toutes les tâches")
    public ResponseEntity<List<Tache>> getAllTaches() {
        List<Tache> taches = tacheRepository.findAll();
        return ResponseEntity.ok(taches);
    }

    /**
     * Endpoint de test pour vérifier que l'API fonctionne
     */
    @GetMapping("/test")
    @Operation(summary = "Test de fonctionnement de l'API")
    public ResponseEntity<String> testApi() {
        return ResponseEntity.ok("API Taches fonctionne correctement !");
    }

    /**
     * Récupérer une tâche par son ID
     */
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir une tâche par ID")
    public ResponseEntity<Tache> getTacheById(@PathVariable Integer id) {
        Optional<Tache> tache = tacheRepository.findById(id);
        return tache.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Récupérer les tâches d'un projet
     */
    @GetMapping("/projet/{projetId}")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir les tâches d'un projet")
    public ResponseEntity<List<Tache>> getTachesByProjet(@PathVariable Integer projetId) {
        Optional<Projet> projet = projetService.getProjetById(projetId);
        if (projet.isPresent()) {
            List<Tache> taches = tacheRepository.findByProjet(projet.get());
            return ResponseEntity.ok(taches);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Récupérer les tâches par statut
     */
    @GetMapping("/statut/{statut}")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir les tâches par statut")
    public ResponseEntity<List<Tache>> getTachesByStatut(@PathVariable String statut) {
        List<Tache> taches = tacheRepository.findByStatut(statut);
        return ResponseEntity.ok(taches);
    }

    /**
     * Récupérer les tâches d'un projet par statut
     */
    @GetMapping("/projet/{projetId}/statut/{statut}")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir les tâches d'un projet par statut")
    public ResponseEntity<List<Tache>> getTachesByProjetAndStatut(
            @PathVariable Integer projetId,
            @PathVariable String statut) {
        
        Optional<Projet> projet = projetService.getProjetById(projetId);
        if (projet.isPresent()) {
            List<Tache> taches = tacheRepository.findByProjetAndStatut(projet.get(), statut);
            return ResponseEntity.ok(taches);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Rechercher des tâches par intitulé
     */
    @GetMapping("/recherche")
    @Transactional(readOnly = true)
    @Operation(summary = "Rechercher des tâches par intitulé")
    public ResponseEntity<List<Tache>> rechercherTaches(@RequestParam String intitule) {
        List<Tache> taches = tacheRepository.findByIntituleContainingIgnoreCase(intitule);
        return ResponseEntity.ok(taches);
    }

    /**
     * Récupérer les tâches qui se terminent dans une période
     */
    @GetMapping("/fin-periode")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir les tâches par période de fin")
    public ResponseEntity<List<Tache>> getTachesByFinPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        
        List<Tache> taches = tacheRepository.findByDateFinBetween(dateDebut, dateFin);
        return ResponseEntity.ok(taches);
    }

    /**
     * Récupérer les tâches en retard
     */
    @GetMapping("/en-retard")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir les tâches en retard")
    public ResponseEntity<List<Tache>> getTachesEnRetard() {
        List<Tache> taches = tacheRepository.findTachesEnRetard(LocalDate.now());
        return ResponseEntity.ok(taches);
    }

    /**
     * Récupérer les tâches en retard pour un projet
     */
    @GetMapping("/projet/{projetId}/en-retard")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir les tâches en retard d'un projet")
    public ResponseEntity<List<Tache>> getTachesEnRetardByProjet(@PathVariable Integer projetId) {
        Optional<Projet> projet = projetService.getProjetById(projetId);
        if (projet.isPresent()) {
            List<Tache> taches = tacheRepository.findTachesEnRetardByProjet(projet.get(), LocalDate.now());
            return ResponseEntity.ok(taches);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Créer une nouvelle tâche
     */
    @PostMapping
    @Operation(summary = "Créer une nouvelle tâche")
    public ResponseEntity<?> createTache(@Valid @RequestBody TacheDTO tacheDTO) {
        try {
            // Log des données reçues pour debug
            System.out.println("TacheDTO reçu: " + tacheDTO.getIntitule() + 
                             ", Projet ID: " + tacheDTO.getProjetId() + 
                             ", Statut: " + tacheDTO.getStatut());
            
            // Vérifier que le projet existe
            if (tacheDTO.getProjetId() == null) {
                return ResponseEntity.badRequest()
                    .body("Erreur: L'ID du projet est obligatoire");
            }
            
            Optional<Projet> projet = projetService.getProjetById(tacheDTO.getProjetId());
            if (!projet.isPresent()) {
                return ResponseEntity.badRequest()
                    .body("Erreur: Le projet avec l'ID " + tacheDTO.getProjetId() + " n'existe pas");
            }
            
            // Créer l'entité Tache à partir du DTO
            Tache tache = new Tache();
            tache.setIntitule(tacheDTO.getIntitule());
            tache.setDateDebut(tacheDTO.getDateDebut());
            tache.setDateFin(tacheDTO.getDateFin());
            tache.setStatut(tacheDTO.getStatut() != null ? tacheDTO.getStatut() : "A_FAIRE");
            tache.setProjet(projet.get());
            
            Tache savedTache = tacheRepository.save(tache);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTache);
            
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de la tâche: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest()
                .body("Erreur lors de la création de la tâche: " + e.getMessage());
        }
    }

    /**
     * Mettre à jour une tâche existante
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier une tâche")
    public ResponseEntity<?> updateTache(
            @PathVariable Integer id, 
            @Valid @RequestBody TacheDTO tacheDTO) {
        
        try {
            Optional<Tache> existingTache = tacheRepository.findById(id);
            if (existingTache.isPresent()) {
                Tache tache = existingTache.get();
                
                // Vérifier que le projet existe
                Optional<Projet> projet = projetService.getProjetById(tacheDTO.getProjetId());
                if (!projet.isPresent()) {
                    return ResponseEntity.badRequest()
                        .body("Erreur: Le projet avec l'ID " + tacheDTO.getProjetId() + " n'existe pas");
                }
                
                // Mettre à jour les champs
                tache.setIntitule(tacheDTO.getIntitule());
                tache.setDateDebut(tacheDTO.getDateDebut());
                tache.setDateFin(tacheDTO.getDateFin());
                tache.setStatut(tacheDTO.getStatut() != null ? tacheDTO.getStatut() : "A_FAIRE");
                tache.setProjet(projet.get());
                
                Tache updatedTache = tacheRepository.save(tache);
                return ResponseEntity.ok(updatedTache);
            }
            return ResponseEntity.notFound().build();
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Erreur lors de la modification de la tâche: " + e.getMessage());
        }
    }

    /**
     * Changer le statut d'une tâche
     */
    @PatchMapping("/{id}/statut")
    @Operation(summary = "Changer le statut d'une tâche")
    public ResponseEntity<?> changerStatutTache(
            @PathVariable Integer id,
            @RequestParam String statut) {
        
        try {
            Optional<Tache> existingTache = tacheRepository.findById(id);
            if (existingTache.isPresent()) {
                Tache tache = existingTache.get();
                tache.setStatut(statut);
                
                Tache updatedTache = tacheRepository.save(tache);
                return ResponseEntity.ok(updatedTache);
            }
            return ResponseEntity.notFound().build();
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Erreur lors du changement de statut: " + e.getMessage());
        }
    }

    /**
     * Supprimer une tâche
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une tâche")
    public ResponseEntity<Void> deleteTache(@PathVariable Integer id) {
        Optional<Tache> tache = tacheRepository.findById(id);
        if (tache.isPresent()) {
            tacheRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Obtenir le nombre total de tâches
     */
    @GetMapping("/count")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir le nombre total de tâches")
    public ResponseEntity<Long> getTachesCount() {
        long count = tacheRepository.count();
        return ResponseEntity.ok(count);
    }

    /**
     * Obtenir le nombre de tâches d'un projet
     */
    @GetMapping("/count/projet/{projetId}")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir le nombre de tâches d'un projet")
    public ResponseEntity<Long> getTachesCountByProjet(@PathVariable Integer projetId) {
        Optional<Projet> projet = projetService.getProjetById(projetId);
        if (projet.isPresent()) {
            long count = tacheRepository.countByProjet(projet.get());
            return ResponseEntity.ok(count);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Obtenir les statistiques des tâches d'un projet par statut
     */
    @GetMapping("/stats/projet/{projetId}")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir les statistiques des tâches d'un projet")
    public ResponseEntity<Map<String, Long>> getStatsTachesByProjet(@PathVariable Integer projetId) {
        Optional<Projet> projet = projetService.getProjetById(projetId);
        if (projet.isPresent()) {
            Map<String, Long> stats = new HashMap<>();
            stats.put("A_FAIRE", tacheRepository.countByProjetAndStatut(projet.get(), "A_FAIRE"));
            stats.put("EN_COURS", tacheRepository.countByProjetAndStatut(projet.get(), "EN_COURS"));
            stats.put("TERMINEE", tacheRepository.countByProjetAndStatut(projet.get(), "TERMINEE"));
            stats.put("EN_ATTENTE", tacheRepository.countByProjetAndStatut(projet.get(), "EN_ATTENTE"));
            stats.put("TOTAL", tacheRepository.countByProjet(projet.get()));
            
            return ResponseEntity.ok(stats);
        }
        return ResponseEntity.notFound().build();
    }
}
