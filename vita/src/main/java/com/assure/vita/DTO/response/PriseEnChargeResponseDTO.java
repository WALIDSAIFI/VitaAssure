package com.assure.vita.DTO.response;

import com.assure.vita.Enum.StatutPriseEnCharge;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PriseEnChargeResponseDTO {
    private Long id;
    private Long utilisateurId;
    private String description;
    private Double montantEstime;
    private LocalDate dateDemande;
    private StatutPriseEnCharge statut;
    private String commentaire;
} 