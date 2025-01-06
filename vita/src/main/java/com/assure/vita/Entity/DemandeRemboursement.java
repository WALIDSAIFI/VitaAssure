package com.assure.vita.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class DemandeRemboursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dossier_id")
    private Dossier dossier;

    @Enumerated(EnumType.STRING)
    private TypeDemande type;

    private Double montant;
    private LocalDate dateDemande;

    @Enumerated(EnumType.STRING)
    private StatutDemande statut;
}
