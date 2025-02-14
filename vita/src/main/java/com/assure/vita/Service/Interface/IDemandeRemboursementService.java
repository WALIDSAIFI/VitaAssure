package com.assure.vita.Service.Interface;

import com.assure.vita.Entity.DemandeRemboursement;
import com.assure.vita.Enum.StatutDemande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IDemandeRemboursementService {
    Page<DemandeRemboursement> getAllDemandes(Pageable pageable);
    Optional<DemandeRemboursement> getDemandeById(Long id);
    List<DemandeRemboursement> getDemandesByDossierId(Long dossierId);
    DemandeRemboursement saveDemande(DemandeRemboursement demande);
    DemandeRemboursement updateDemande(Long id, DemandeRemboursement demande);
    void deleteDemande(Long id);
    DemandeRemboursement updateStatutDemande(Long id, StatutDemande statut);
    DemandeRemboursement rejeterDemande(Long id, String motif);
    Page<DemandeRemboursement> getDemandesByDossier(Long dossierId, Pageable pageable);
}
