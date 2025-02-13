package com.assure.vita.DTO.request;

import com.assure.vita.Enum.StatutDemande;
import com.assure.vita.Enum.TypeDemande;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DemandeRemboursementRequestDTO {
    private Long dossierId;
    private TypeDemande type;
    private Double montant;
    private LocalDate dateDemande;
    private StatutDemande statut;
}