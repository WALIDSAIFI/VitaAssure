package com.assure.vita.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "demande_remboursement")
public class DemandeRemboursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_id", nullable = false)
    private Dossier dossier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeDemande type;

    @Column(nullable = false)
    private Double montant;

    @Column(name = "date_demande", nullable = false)
    private LocalDate dateDemande;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDemande statut;
}
