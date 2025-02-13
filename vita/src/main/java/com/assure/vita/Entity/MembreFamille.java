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

    @Enumerated(EnumType.STRING)
    private LienParente lienParente; // ENFANT ou CONJOINT

    @ManyToOne
    @JoinColumn(name = "assure_id")
    private Utilisateur assure;
}
