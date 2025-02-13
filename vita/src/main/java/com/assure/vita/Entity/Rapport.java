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
@Table(name = "rapport")
public class Rapport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_remboursement_id", nullable = false)
    private DemandeRemboursement demandeRemboursement;

    @Column(nullable = false)
    private String details;

    @Column(name = "date_rapport", nullable = false)
    private LocalDate dateRapport;
} 