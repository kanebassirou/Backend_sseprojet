package com.sseprojet.sseprojet.service;

import com.sseprojet.sseprojet.model.Administrateur;
import com.sseprojet.sseprojet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataInitializationService implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Créer un administrateur par défaut si aucun utilisateur n'existe
        if (userRepository.count() == 0) {
            System.out.println("🔧 Initialisation des données : Création de l'administrateur par défaut");
            
            Administrateur admin = new Administrateur();
            admin.setNom("Administrateur");
            admin.setEmail("admin@sseprojet.com");
            admin.setMotDePasseHash(passwordEncoder.encode("admin123"));
            
            userRepository.save(admin);
            
            System.out.println("✅ Administrateur créé : admin@sseprojet.com / admin123");
        }
    }
}
