package com.assure.vita.Service.Interface;

import com.assure.vita.Entity.Dossier;
import com.assure.vita.Enum.StatutDossier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IDossierService {
    Page<Dossier> getAllDossiers(Pageable pageable);
    Page<Dossier> getDossiersByUtilisateurId(Long utilisateurId, Pageable pageable);
    Page<Dossier> getDossiersByStatut(StatutDossier statut, Pageable pageable);
    Optional<Dossier> getDossierById(Long id);
    Dossier saveDossier(Dossier dossier);
    Dossier accepterDossier(Long id);
    Dossier rejeterDossier(Long id, String motifRejet);
    public Dossier updateDossier(Long id, Dossier updatedDossier);
    public void deleteDossier(Long id);
}
