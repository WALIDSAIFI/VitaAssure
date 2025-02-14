package com.assure.vita.DTO.request;

import com.assure.vita.Enum.SituationFamiliale;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UtilisateurUpdateDTO {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;
    
    @Email(message = "Format d'email invalide")
    private String email;
    
    private LocalDate dateNaissance;
    private String adresse;
    private String telephone;
    private SituationFamiliale situationFamiliale;
} 