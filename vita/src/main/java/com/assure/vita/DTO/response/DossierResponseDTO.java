package com.assure.vita.DTO.response;

import com.assure.vita.Enum.StatutDossier;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DossierResponseDTO {
    private Long id;
    private Long utilisateurId;
    private StatutDossier statut;
    private String commentaire;
    private LocalDate dateTraitement;
} 