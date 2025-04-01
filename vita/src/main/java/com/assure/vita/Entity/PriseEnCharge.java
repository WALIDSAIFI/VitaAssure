package com.assure.vita.Entity;

import com.assure.vita.Enum.StatutPriseEnCharge;
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
@Table(name = "prise_en_charge")
public class PriseEnCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double montantEstime;

    @Column(name = "date_demande", nullable = false)
    private LocalDate dateDemande;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPriseEnCharge statut;

    @Column(length = 500)
    private String commentaire;

} 