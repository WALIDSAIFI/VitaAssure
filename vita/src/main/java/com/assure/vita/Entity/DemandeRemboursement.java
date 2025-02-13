package com.assure.vita.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "demande_remboursement")
public class DemandeRemboursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_id", nullable = false, unique = true)
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
