package com.assure.vita.Config;

import com.assure.vita.Entity.Utilisateur;
import com.assure.vita.Enum.Role;
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
        // Créer le compte admin s'il n'existe pas
        if (!utilisateurRepository.findByEmail("admin@vita.com").isPresent()) {
            Utilisateur admin = new Utilisateur();
            admin.setNom("Admin");
            admin.setPrenom("Vita");
            admin.setEmail("admin@vita.com");
            admin.setPassword(passwordEncoder.encode("vita123"));
            admin.setRole(Role.ADMINISTRATEUR);
            admin.setDateNaissance(LocalDate.of(1998, 7, 2));
            admin.setValider(true);
            
            utilisateurRepository.save(admin);
        }
    }
} 