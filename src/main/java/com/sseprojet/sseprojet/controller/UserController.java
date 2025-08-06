package com.sseprojet.sseprojet.controller;

import com.sseprojet.sseprojet.dto.CreateUserRequest;
import com.sseprojet.sseprojet.dto.UserResponse;
import com.sseprojet.sseprojet.factory.UserFactory;
import com.sseprojet.sseprojet.model.User;
import com.sseprojet.sseprojet.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des utilisateurs
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Tag(name = "Users", description = "API de gestion des  users du système SSE")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserFactory userFactory;
    
    @Operation(
        summary = "Récupérer tous les utilisateurs",
        description = "Retourne la liste complète de tous les utilisateurs du système"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée avec succès",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> userResponses = users.stream()
                .map(UserResponse::fromUser)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userResponses);
    }
    
    @Operation(
        summary = "Récupérer un utilisateur par ID",
        description = "Retourne les détails d'un utilisateur spécifique basé sur son ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur trouvé",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "ID de l'utilisateur à récupérer", example = "1")
            @PathVariable Integer id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(u -> ResponseEntity.ok(UserResponse.fromUser(u)))
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(
        summary = "Récupérer un utilisateur par email",
        description = "Retourne les détails d'un utilisateur basé sur son adresse email"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(
            @Parameter(description = "Email de l'utilisateur", example = "admin@exemple.com")
            @PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(
        summary = "Créer un nouvel utilisateur",
        description = "Crée un nouvel utilisateur dans le système selon le type spécifié"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "409", description = "Email déjà utilisé")
    })
    @PostMapping
    public ResponseEntity<User> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Données de création de l'utilisateur",
                content = @Content(schema = @Schema(implementation = CreateUserRequest.class))
            )
            @Valid @RequestBody CreateUserRequest request) {
        try {
            if (userService.existsByEmail(request.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            User user = userFactory.createUser(request);
            User savedUser = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(
        summary = "Mettre à jour un utilisateur",
        description = "Met à jour les informations d'un utilisateur existant"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "ID de l'utilisateur à modifier")
            @PathVariable Integer id, 
            @RequestBody User user) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            user.setId(id);
            User updatedUser = userService.saveUser(user);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(
        summary = "Supprimer un utilisateur",
        description = "Supprime un utilisateur du système"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Utilisateur supprimé"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID de l'utilisateur à supprimer")
            @PathVariable Integer id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
