package com.assure.vita.Service.Interface;

import com.assure.vita.Entity.Dossier;
import com.assure.vita.Enum.StatutDossier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IDossierService {
    Page<Dossier> getAllDossiers(Pageable pageable);
    Optional<Dossier> getDossierById(Long id);
    Page<Dossier> getDossiersByUtilisateurId(Long utilisateurId, Pageable pageable);
    Page<Dossier> getDossiersByStatut(StatutDossier statut, Pageable pageable);
    Dossier saveDossier(Dossier dossier);
    Dossier updateDossier(Long id, Dossier dossier);
    void deleteDossier(Long id);
    Dossier rejeterDossier(Long id, String motifRejet);
    Dossier accepterDossier(Long id);
}
