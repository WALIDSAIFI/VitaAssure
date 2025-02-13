package com.assure.vita.DTO.response;

import lombok.Data;
import java.util.List;

@Data
public class DossierResponseDTO {
    private Long id;
    private Long utilisateurId;
    private String statut;
    private List<DemandeRemboursementResponseDTO> demandes;
} 