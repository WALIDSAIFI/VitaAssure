package com.assure.vita.Service.Interface;

import com.assure.vita.Entity.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface IUtilisateurService {
    List<Utilisateur> getAllUtilisateurs();
    Optional<Utilisateur> getUtilisateurById(Long id);
    Optional<Utilisateur> getUtilisateurByEmail(String email);
    Utilisateur saveUtilisateur(Utilisateur utilisateur);
    void deleteUtilisateur(Long id);
}
