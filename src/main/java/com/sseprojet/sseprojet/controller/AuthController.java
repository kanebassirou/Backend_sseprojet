package com.sseprojet.sseprojet.controller;

import com.sseprojet.sseprojet.dto.JwtResponse;
import com.sseprojet.sseprojet.dto.LoginRequest;
import com.sseprojet.sseprojet.dto.RegisterRequest;
import com.sseprojet.sseprojet.model.*;
import com.sseprojet.sseprojet.repository.UserRepository;
import com.sseprojet.sseprojet.security.JwtUtils;
import com.sseprojet.sseprojet.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Authentification", description = "API d'authentification JWT")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "Connexion utilisateur", description = "Authentifie un utilisateur et retourne un token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Connexion réussie"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "401", description = "Identifiants incorrects")
    })
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getMotDePasse()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            
            return ResponseEntity.ok(new JwtResponse(
                jwt,
                userPrincipal.getId(),
                userPrincipal.getEmail(),
                userPrincipal.getNom(),
                userPrincipal.getRole()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Erreur : Email ou mot de passe incorrect");
        }
    }

    @GetMapping("/me")
    @Operation(summary = "Profil utilisateur", description = "Retourne les informations de l'utilisateur connecté")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profil récupéré"),
        @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    public ResponseEntity<?> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Non authentifié");
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        return ResponseEntity.ok(new JwtResponse(
            null, // Pas besoin de renvoyer le token
            userPrincipal.getId(),
            userPrincipal.getEmail(),
            userPrincipal.getNom(),
            userPrincipal.getRole()
        ));
    }

    @PostMapping("/logout")
    @Operation(summary = "Déconnexion", description = "Déconnecte l'utilisateur (côté client)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Déconnexion réussie")
    })
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok().body("Déconnexion réussie. Supprimez le token côté client.");
    }

    @GetMapping("/test")
    @Operation(summary = "Test API", description = "Endpoint de test pour vérifier que l'API fonctionne")
    public ResponseEntity<String> testApi() {
        return ResponseEntity.ok("API d'authentification fonctionne !");
    }

    @PostMapping("/init-admin")
    @Operation(summary = "Initialiser Admin", description = "Créer l'administrateur par défaut (DEV uniquement)")
    public ResponseEntity<String> initAdmin() {
        try {
            // Vérifier si admin existe déjà
            var existingAdmin = userRepository.findByEmail("admin@sseprojet.com");
            if (existingAdmin.isPresent()) {
                return ResponseEntity.ok("ℹ️ Administrateur existe déjà : admin@sseprojet.com");
            }
            
            // Créer l'admin
            Administrateur admin = new Administrateur();
            admin.setNom("Administrateur");
            admin.setEmail("admin@sseprojet.com");
            admin.setMotDePasseHash(passwordEncoder.encode("admin123"));
            
            userRepository.save(admin);
            return ResponseEntity.ok("✅ Administrateur créé : admin@sseprojet.com / admin123");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Erreur lors de la création : " + e.getMessage());
        }
    }

    @GetMapping("/users-count")
    @Operation(summary = "Compter les utilisateurs", description = "Retourne le nombre d'utilisateurs en base")
    public ResponseEntity<String> getUsersCount() {
        long count = userRepository.count();
        return ResponseEntity.ok("Nombre d'utilisateurs : " + count);
    }

    @PostMapping("/test-login")
    @Operation(summary = "Test de connexion simple", description = "Test de connexion avec validation directe")
    public ResponseEntity<String> testLogin(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Vérifier si l'utilisateur existe
            var userOpt = userRepository.findByEmail(loginRequest.getEmail());
            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("❌ Utilisateur non trouvé avec l'email : " + loginRequest.getEmail());
            }
            
            var user = userOpt.get();
            
            // Vérifier le mot de passe
            boolean passwordMatches = passwordEncoder.matches(loginRequest.getMotDePasse(), user.getMotDePasseHash());
            
            return ResponseEntity.ok(String.format(
                "✅ Utilisateur trouvé : %s (ID: %d, Rôle: %s) - Mot de passe correct : %s", 
                user.getNom(), 
                user.getId(), 
                user.getRole(),
                passwordMatches ? "✅ OUI" : "❌ NON"
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Erreur : " + e.getMessage());
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Inscription utilisateur", description = "Créer un nouveau compte utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides ou email déjà utilisé"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            // Vérifier si l'email existe déjà
            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                return ResponseEntity.badRequest()
                    .body("❌ Erreur : Cet email est déjà utilisé");
            }

            // Créer l'utilisateur selon le type demandé
            User newUser = createUserByType(registerRequest);
            
            if (newUser == null) {
                return ResponseEntity.badRequest()
                    .body("❌ Erreur : Type d'utilisateur invalide");
            }

            // Encoder le mot de passe
            newUser.setMotDePasseHash(passwordEncoder.encode(registerRequest.getMotDePasse()));
            
            // Sauvegarder
            User savedUser = userRepository.save(newUser);
            
            return ResponseEntity.status(201).body(String.format(
                "✅ Utilisateur créé avec succès : %s (%s) - ID: %d", 
                savedUser.getNom(), 
                savedUser.getEmail(),
                savedUser.getId()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body("❌ Erreur lors de la création : " + e.getMessage());
        }
    }

    private User createUserByType(RegisterRequest request) {
        switch (request.getTypeUtilisateur().toUpperCase()) {
            case "ADMINISTRATEUR":
                Administrateur admin = new Administrateur();
                admin.setNom(request.getNom());
                admin.setEmail(request.getEmail());
                return admin;
                
            case "CHEF_PROJET":
                ChefDeProjet chef = new ChefDeProjet();
                chef.setNom(request.getNom());
                chef.setEmail(request.getEmail());
                return chef;
                
            case "DECIDEUR":
                Decideur decideur = new Decideur();
                decideur.setNom(request.getNom());
                decideur.setEmail(request.getEmail());
                return decideur;
                
            case "EVALUATEUR":
                Evaluateur evaluateur = new Evaluateur();
                evaluateur.setNom(request.getNom());
                evaluateur.setEmail(request.getEmail());
                return evaluateur;
                
            default:
                return null;
        }
    }

    @GetMapping("/list-users")
    @Operation(summary = "Lister les utilisateurs", description = "Liste tous les utilisateurs (DEV uniquement)")
    public ResponseEntity<String> listUsers() {
        try {
            var users = userRepository.findAll();
            StringBuilder sb = new StringBuilder("📋 Utilisateurs en base :\n");
            
            for (var user : users) {
                sb.append(String.format("- ID: %d | Email: %s | Nom: %s | Rôle: %s\n", 
                    user.getId(), user.getEmail(), user.getNom(), user.getRole()));
            }
            
            return ResponseEntity.ok(sb.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Erreur : " + e.getMessage());
        }
    }
}
