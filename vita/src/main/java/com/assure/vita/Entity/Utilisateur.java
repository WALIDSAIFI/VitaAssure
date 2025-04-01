package com.assure.vita.Entity;

import com.assure.vita.Enum.Role;
import com.assure.vita.Enum.SituationFamiliale;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    private String adresse;

    private String telephone;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "situation_familiale")
    private SituationFamiliale situationFamiliale;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.ADHERENT;

    @Column(name = "valider", nullable = false)
    private Boolean valider = false;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dossier> dossiers;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MembreFamille> membresFamille;
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PriseEnCharge> prisesEnCharge;

}
