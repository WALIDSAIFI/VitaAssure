package com.assure.vita.DTO.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AssureRequestDTO {
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String adresse;
    private String telephone;
    private String email;
} 