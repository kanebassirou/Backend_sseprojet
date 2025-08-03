package com.sseprojet.sseprojet.controller;

import com.sseprojet.sseprojet.dto.RapportDTO;
import com.sseprojet.sseprojet.model.Rapport;
import com.sseprojet.sseprojet.model.Projet;
import com.sseprojet.sseprojet.repository.RapportRepository;
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
import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des rapports
 */
@RestController
@RequestMapping("/api/rapports")
@CrossOrigin(origins = "*")
@Tag(name = "Rapports", description = "API de gestion des rapports de projet")
public class RapportController {

    @Autowired
    private RapportRepository rapportRepository;

    @Autowired
    private ProjetService projetService;

    /**
     * Récupérer tous les rapports
     */
    @GetMapping
    @Transactional(readOnly = true)
    @Operation(summary = "Lister tous les rapports")
    public ResponseEntity<List<Rapport>> getAllRapports() {
        List<Rapport> rapports = rapportRepository.findAll();
        return ResponseEntity.ok(rapports);
    }

    /**
     * Endpoint de test pour vérifier que l'API fonctionne
     */
    @GetMapping("/test")
    @Operation(summary = "Test de fonctionnement de l'API")
    public ResponseEntity<String> testApi() {
        return ResponseEntity.ok("API Rapports fonctionne correctement !");
    }

    /**
     * Récupérer un rapport par son ID
     */
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir un rapport par ID")
    public ResponseEntity<Rapport> getRapportById(@PathVariable Integer id) {
        Optional<Rapport> rapport = rapportRepository.findById(id);
        return rapport.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Récupérer les rapports d'un projet
     */
    @GetMapping("/projet/{projetId}")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir les rapports d'un projet")
    public ResponseEntity<List<Rapport>> getRapportsByProjet(@PathVariable Integer projetId) {
        Optional<Projet> projet = projetService.getProjetById(projetId);
        if (projet.isPresent()) {
            List<Rapport> rapports = rapportRepository.findByProjet(projet.get());
            return ResponseEntity.ok(rapports);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Récupérer les rapports d'un auteur
     */
    @GetMapping("/auteur/{auteur}")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir les rapports d'un auteur")
    public ResponseEntity<List<Rapport>> getRapportsByAuteur(@PathVariable String auteur) {
        List<Rapport> rapports = rapportRepository.findByAuteur(auteur);
        return ResponseEntity.ok(rapports);
    }

    /**
     * Rechercher des rapports par titre
     */
    @GetMapping("/recherche")
    @Transactional(readOnly = true)
    @Operation(summary = "Rechercher des rapports par titre")
    public ResponseEntity<List<Rapport>> rechercherRapports(@RequestParam String titre) {
        List<Rapport> rapports = rapportRepository.findByTitreContainingIgnoreCase(titre);
        return ResponseEntity.ok(rapports);
    }

    /**
     * Récupérer les rapports entre deux dates
     */
    @GetMapping("/periode")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir les rapports par période")
    public ResponseEntity<List<Rapport>> getRapportsByPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        
        List<Rapport> rapports = rapportRepository.findByDateGenerationBetween(dateDebut, dateFin);
        return ResponseEntity.ok(rapports);
    }

    /**
     * Récupérer les rapports d'un projet entre deux dates
     */
    @GetMapping("/projet/{projetId}/periode")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir les rapports d'un projet par période")
    public ResponseEntity<List<Rapport>> getRapportsByProjetAndPeriode(
            @PathVariable Integer projetId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        
        Optional<Projet> projet = projetService.getProjetById(projetId);
        if (projet.isPresent()) {
            List<Rapport> rapports = rapportRepository.findByProjetAndDateGenerationBetween(
                projet.get(), dateDebut, dateFin);
            return ResponseEntity.ok(rapports);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Récupérer les rapports d'un auteur pour un projet
     */
    @GetMapping("/projet/{projetId}/auteur/{auteur}")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir les rapports d'un auteur pour un projet")
    public ResponseEntity<List<Rapport>> getRapportsByAuteurAndProjet(
            @PathVariable Integer projetId,
            @PathVariable String auteur) {
        
        Optional<Projet> projet = projetService.getProjetById(projetId);
        if (projet.isPresent()) {
            List<Rapport> rapports = rapportRepository.findByAuteurAndProjet(auteur, projet.get());
            return ResponseEntity.ok(rapports);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Créer un nouveau rapport
     */
    @PostMapping
    @Operation(summary = "Créer un nouveau rapport")
    public ResponseEntity<?> createRapport(@Valid @RequestBody RapportDTO rapportDTO) {
        try {
            // Log des données reçues pour debug
            System.out.println("RapportDTO reçu: " + rapportDTO.getTitre() + 
                             ", Projet ID: " + rapportDTO.getProjetId() + 
                             ", Auteur: " + rapportDTO.getAuteur());
            
            // Vérifier que le projet existe
            if (rapportDTO.getProjetId() == null) {
                return ResponseEntity.badRequest()
                    .body("Erreur: L'ID du projet est obligatoire");
            }
            
            Optional<Projet> projet = projetService.getProjetById(rapportDTO.getProjetId());
            if (!projet.isPresent()) {
                return ResponseEntity.badRequest()
                    .body("Erreur: Le projet avec l'ID " + rapportDTO.getProjetId() + " n'existe pas");
            }
            
            // Créer l'entité Rapport à partir du DTO
            Rapport rapport = new Rapport();
            rapport.setTitre(rapportDTO.getTitre());
            rapport.setDateGeneration(rapportDTO.getDateGeneration() != null ? 
                                    rapportDTO.getDateGeneration() : LocalDate.now());
            rapport.setContenu(rapportDTO.getContenu());
            rapport.setAuteur(rapportDTO.getAuteur());
            rapport.setProjet(projet.get());
            
            Rapport savedRapport = rapportRepository.save(rapport);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRapport);
            
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du rapport: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest()
                .body("Erreur lors de la création du rapport: " + e.getMessage());
        }
    }

    /**
     * Mettre à jour un rapport existant
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un rapport")
    public ResponseEntity<Rapport> updateRapport(
            @PathVariable Integer id, 
            @Valid @RequestBody RapportDTO rapportDTO) {
        
        Optional<Rapport> existingRapport = rapportRepository.findById(id);
        if (existingRapport.isPresent()) {
            Rapport rapport = existingRapport.get();
            
            // Vérifier que le projet existe
            Optional<Projet> projet = projetService.getProjetById(rapportDTO.getProjetId());
            if (!projet.isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            
            // Mettre à jour les champs
            rapport.setTitre(rapportDTO.getTitre());
            rapport.setDateGeneration(rapportDTO.getDateGeneration());
            rapport.setContenu(rapportDTO.getContenu());
            rapport.setAuteur(rapportDTO.getAuteur());
            rapport.setProjet(projet.get());
            
            Rapport updatedRapport = rapportRepository.save(rapport);
            return ResponseEntity.ok(updatedRapport);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Supprimer un rapport
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un rapport")
    public ResponseEntity<Void> deleteRapport(@PathVariable Integer id) {
        Optional<Rapport> rapport = rapportRepository.findById(id);
        if (rapport.isPresent()) {
            rapportRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Obtenir le nombre total de rapports
     */
    @GetMapping("/count")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir le nombre total de rapports")
    public ResponseEntity<Long> getRapportsCount() {
        long count = rapportRepository.count();
        return ResponseEntity.ok(count);
    }

    /**
     * Obtenir le nombre de rapports d'un projet
     */
    @GetMapping("/count/projet/{projetId}")
    @Transactional(readOnly = true)
    @Operation(summary = "Obtenir le nombre de rapports d'un projet")
    public ResponseEntity<Long> getRapportsCountByProjet(@PathVariable Integer projetId) {
        Optional<Projet> projet = projetService.getProjetById(projetId);
        if (projet.isPresent()) {
            long count = rapportRepository.countByProjet(projet.get());
            return ResponseEntity.ok(count);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Générer un rapport automatique (exemple)
     */
    @PostMapping("/generer-auto/{projetId}")
    @Operation(summary = "Générer un rapport automatique pour un projet")
    public ResponseEntity<Rapport> genererRapportAuto(@PathVariable Integer projetId) {
        try {
            Optional<Projet> projet = projetService.getProjetById(projetId);
            if (!projet.isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            
            // Créer un rapport automatique
            Rapport rapport = new Rapport();
            rapport.setTitre("Rapport automatique - " + projet.get().getTitre());
            rapport.setDateGeneration(LocalDate.now());
            rapport.setContenu("Rapport généré automatiquement le " + LocalDate.now() + 
                             " pour le projet: " + projet.get().getTitre());
            rapport.setAuteur("Système");
            rapport.setProjet(projet.get());
            
            Rapport savedRapport = rapportRepository.save(rapport);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRapport);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
