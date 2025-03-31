package com.assure.vita.Entity;

import com.assure.vita.Enum.StatutDossier;
import com.assure.vita.Enum.TypeTraitement;
import jakarta.persistence.*;
import lombok.*;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDossier statut = StatutDossier.EN_ATTENTE;

    @Enumerated(EnumType.STRING)
    private TypeTraitement typeTraitement;

    @Column(nullable = false)
    private Double totalFrais;

    @Column(length = 500)
    private String commentaire;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDate dateCreation;

    @Column(name = "date_traitement")
    private LocalDate dateTraitement;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDate.now();
    }
}
