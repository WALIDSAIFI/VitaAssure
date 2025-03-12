package com.assure.vita.Config;

import com.assure.vita.Entity.Utilisateur;
import com.assure.vita.Enum.Role;
import com.assure.vita.Enum.SituationFamiliale;
import com.assure.vita.Repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Créer un admin par défaut si aucun n'existe
        if (utilisateurRepository.findByEmail("admin@vita.com").isEmpty()) {
            Utilisateur admin = new Utilisateur();
            admin.setNom("Admin");
            admin.setPrenom("System");
            admin.setEmail("admin@vita.com");
            admin.setPassword(passwordEncoder.encode("Admin123"));
            admin.setRole(Role.ADMINISTRATEUR);
            admin.setValider(true);
            admin.setDateNaissance(LocalDate.of(1990, 1, 1)); // Date de naissance par défaut
            admin.setSituationFamiliale(SituationFamiliale.CELIBATAIRE); // Situation familiale par défaut
            admin.setAdresse("1 Rue de l'Administration"); // Adresse par défaut
            admin.setTelephone("06234569"); // Téléphone par défaut
            
            utilisateurRepository.save(admin);
        }
    }
} 