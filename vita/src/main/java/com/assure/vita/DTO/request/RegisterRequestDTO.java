package com.assure.vita.DTO.request;

import com.assure.vita.Entity.SituationFamiliale;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    @NotNull(message = "La date de naissance est obligatoire")
    private LocalDate dateNaissance;

    private String adresse;
    private String telephone;
    private SituationFamiliale situationFamiliale;
} 