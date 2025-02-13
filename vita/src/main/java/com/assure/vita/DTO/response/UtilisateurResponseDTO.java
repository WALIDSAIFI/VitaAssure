package com.assure.vita.DTO.response;

import com.assure.vita.Entity.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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
} 