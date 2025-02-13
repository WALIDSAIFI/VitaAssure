package com.assure.vita.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MembreFamille {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String lienParente;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
}
