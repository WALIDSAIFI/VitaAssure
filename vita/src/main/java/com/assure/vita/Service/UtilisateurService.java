package com.assure.vita.Service;

import com.assure.vita.DTO.request.UtilisateurRequestDTO;
import com.assure.vita.DTO.response.UtilisateurResponseDTO;
import java.util.List;

public interface UtilisateurService {
    UtilisateurResponseDTO createUtilisateur(UtilisateurRequestDTO utilisateurRequestDTO);
    UtilisateurResponseDTO getUtilisateurById(Long id);
    List<UtilisateurResponseDTO> getAllUtilisateurs();
    UtilisateurResponseDTO updateUtilisateur(Long id, UtilisateurRequestDTO utilisateurRequestDTO);
    void deleteUtilisateur(Long id);
} 