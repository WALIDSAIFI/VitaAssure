package com.assure.vita.Entity;

import com.assure.vita.Enum.StatutDossier;
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
@Table(name = "dossier")
public class Dossier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @OneToOne(mappedBy = "dossier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private DemandeRemboursement demande;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDossier statut = StatutDossier.EN_ATTENTE;

    @Column(length = 500)
    private String commentaire;

    @Column(name = "date_traitement")
    private LocalDate dateTraitement;
}
