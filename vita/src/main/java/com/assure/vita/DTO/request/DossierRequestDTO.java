package com.assure.vita.DTO.request;

import com.assure.vita.Enum.StatutDossier;
import com.assure.vita.Enum.TypeTraitement;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DossierRequestDTO {
    @NotNull(message = "L'ID de l'utilisateur est obligatoire")
    private Long utilisateurId;

    private TypeTraitement typeTraitement = TypeTraitement.HOSPITALISATION;


    private String commentaire;

    private StatutDossier statut = StatutDossier.EN_ATTENTE;

    private Double totalFrais ;
} 