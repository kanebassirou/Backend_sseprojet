package com.sseprojet.sseprojet.controller;
import com.sseprojet.sseprojet.model.Indicateur;
import com.sseprojet.sseprojet.model.Projet;
import com.sseprojet.sseprojet.repository.IndicateurRepository;
import com.sseprojet.sseprojet.service.ProjetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des indicateurs
 */
@RestController
@RequestMapping("/api/indicateurs")
@CrossOrigin(origins = "*")
@Tag(name = "Indicateurs", description = "API de gestion des indicateurs de performance")
public class IndicateurController {
    
    @Autowired
    private IndicateurRepository indicateurRepository;
    
    @Autowired
    private ProjetService projetService;
    
    @GetMapping
    public ResponseEntity<List<Indicateur>> getAllIndicateurs() {
        List<Indicateur> indicateurs = indicateurRepository.findAll();
        return ResponseEntity.ok(indicateurs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Indicateur> getIndicateurById(@PathVariable Integer id) {
        Optional<Indicateur> indicateur = indicateurRepository.findById(id);
        return indicateur.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/projet/{projetId}")
    public ResponseEntity<List<Indicateur>> getIndicateursByProjet(@PathVariable Integer projetId) {
        Optional<Projet> projet = projetService.getProjetById(projetId);
        if (projet.isPresent()) {
            List<Indicateur> indicateurs = indicateurRepository.findByProjet(projet.get());
            return ResponseEntity.ok(indicateurs);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Indicateur>> getIndicateursByType(@PathVariable String type) {
        List<Indicateur> indicateurs = indicateurRepository.findByType(type);
        return ResponseEntity.ok(indicateurs);
    }
    
    @PostMapping
    public ResponseEntity<Indicateur> createIndicateur(@RequestBody Indicateur indicateur) {
        try {
            Indicateur savedIndicateur = indicateurRepository.save(indicateur);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedIndicateur);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Indicateur> updateIndicateur(@PathVariable Integer id, @RequestBody Indicateur indicateur) {
        Optional<Indicateur> existingIndicateur = indicateurRepository.findById(id);
        if (existingIndicateur.isPresent()) {
            indicateur.setId(id);
            Indicateur updatedIndicateur = indicateurRepository.save(indicateur);
            return ResponseEntity.ok(updatedIndicateur);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndicateur(@PathVariable Integer id) {
        Optional<Indicateur> indicateur = indicateurRepository.findById(id);
        if (indicateur.isPresent()) {
            indicateurRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
