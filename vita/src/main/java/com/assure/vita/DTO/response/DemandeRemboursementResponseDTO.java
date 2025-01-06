package com.assure.vita.DTO.response;

import com.assure.vita.Entity.StatutDemande;
import com.assure.vita.Entity.TypeDemande;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DemandeRemboursementResponseDTO {
    private Long id;
    private Long dossierId;
    private TypeDemande type;
    private Double montant;
    private LocalDate dateDemande;
    private StatutDemande statut;
} 