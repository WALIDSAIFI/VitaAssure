package com.assure.vita.DTO.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UtilisateurRequestDTO {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String adresse;
    private String telephone;
    private String email;
    private Boolean valider;
} 