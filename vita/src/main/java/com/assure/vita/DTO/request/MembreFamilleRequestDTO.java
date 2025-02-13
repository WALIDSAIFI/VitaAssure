package com.assure.vita.DTO.request;


import com.assure.vita.Entity.LienParente;
import lombok.Data;

@Data
public class MembreFamilleRequestDTO {
    private String nom;
    private String prenom;
    private LienParente lienParente;
    private Long userId;
}
