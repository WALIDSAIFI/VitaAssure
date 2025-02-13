package com.assure.vita.Service.Interface;

import com.assure.vita.Entity.Dossier;

import java.util.List;
import java.util.Optional;

public interface IDossierService {
    List<Dossier> getAllDossiers();
    Optional<Dossier> getDossierById(Long id);
    List<Dossier> getDossiersByUtilisateurId(Long utilisateurId);
    Dossier saveDossier(Dossier dossier);
    Dossier updateDossier(Long id, Dossier dossier);
    void deleteDossier(Long id);
    Dossier rejeterDossier(Long id, String motifRejet);
    Dossier accepterDossier(Long id);
}
