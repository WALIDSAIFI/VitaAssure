package com.assure.vita.Service.Interface;

import com.assure.vita.Entity.MembreFamille;
import java.util.List;
import java.util.Optional;

public interface IMembreFamilleService {
    List<MembreFamille> getAllMembresFamille();
    Optional<MembreFamille> getMembreFamilleById(Long id);
    List<MembreFamille> getMembresFamilleByUtilisateurId(Long utilisateurId);
    MembreFamille saveMembreFamille(MembreFamille membreFamille);
    MembreFamille updateMembreFamille(Long id, MembreFamille membreFamille);
    void deleteMembreFamille(Long id);
} 