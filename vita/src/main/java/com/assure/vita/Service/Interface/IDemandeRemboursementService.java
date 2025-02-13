package com.assure.vita.Service.Interface;

import com.assure.vita.Entity.DemandeRemboursement;

import java.util.List;
import java.util.Optional;

public interface IDemandeRemboursementService {
    List<DemandeRemboursement> getAllDemandes();
    Optional<DemandeRemboursement> getDemandeById(Long id);
    List<DemandeRemboursement> getDemandesByDossierId(Long dossierId);
    DemandeRemboursement saveDemande(DemandeRemboursement demande);
    void deleteDemande(Long id);
}
