package com.assure.vita.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

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
    private SituationFamiliale situationFamiliale;

    @OneToMany(mappedBy = "assure", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MembreFamille> membresFamille;
}
