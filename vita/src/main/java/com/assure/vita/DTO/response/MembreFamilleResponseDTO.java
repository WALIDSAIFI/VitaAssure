package com.assure.vita.DTO.response;

import com.assure.vita.Entity.LienParente;
import lombok.Data;

@Data
public class MembreFamilleResponseDTO {
    private Long id;
    private String nom;
    private String prenom;
    private LienParente lienParente;
    private Long assureId;
}
