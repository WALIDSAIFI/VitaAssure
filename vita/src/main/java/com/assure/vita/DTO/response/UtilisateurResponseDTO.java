package com.assure.vita.DTO.response;

import com.assure.vita.Enum.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UtilisateurResponseDTO {
    private Long id;

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String adresse;
    private String telephone;
    private String email;
    private Role role;
    private Boolean valider;
} 