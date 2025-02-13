package com.assure.vita.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String adresse;
    private String telephone;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}
