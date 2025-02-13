package com.assure.vita.DTO.request;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;

@Data
@Getter
@Setter
public class DossierRequestDTO {
    private Long utilisateurId;
    private String statut;
} 