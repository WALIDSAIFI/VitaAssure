package com.assure.vita.Service.impl;

import com.assure.vita.Entity.DemandeRemboursement;
import com.assure.vita.Entity.Dossier;
import com.assure.vita.Exception.ResourceNotFoundException;
import com.assure.vita.Exception.BadRequestException;
import com.assure.vita.Repository.DemandeRemboursementRepository;
import com.assure.vita.Repository.DossierRepository;
import com.assure.vita.Service.Interface.IDemandeRemboursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DemandeRemboursementServiceImpl implements IDemandeRemboursementService {

    private final DemandeRemboursementRepository demandeRepository;
    private final DossierRepository dossierRepository;

    @Autowired
    public DemandeRemboursementServiceImpl(DemandeRemboursementRepository demandeRepository,
                                         DossierRepository dossierRepository) {
        this.demandeRepository = demandeRepository;
        this.dossierRepository = dossierRepository;
    }

    @Override
    public List<DemandeRemboursement> getAllDemandes() {
        return demandeRepository.findAll();
    }

    @Override
    public Optional<DemandeRemboursement> getDemandeById(Long id) {
        return demandeRepository.findById(id);
    }

    @Override
    public List<DemandeRemboursement> getDemandesByDossierId(Long dossierId) {
        return demandeRepository.findByDossierId(dossierId);
    }

    @Override
    @Transactional
    public DemandeRemboursement saveDemande(DemandeRemboursement demande) {
        if (demande.getDossier() == null || demande.getDossier().getId() == null) {
            throw new BadRequestException("Le dossier est obligatoire");
        }

        Optional<Dossier> dossier = dossierRepository.findById(demande.getDossier().getId());
        if (dossier.isEmpty()) {
            throw new ResourceNotFoundException("Dossier non trouvé avec l'ID : " + demande.getDossier().getId());
        }

        if (demande.getDateDemande() == null) {
            demande.setDateDemande(LocalDate.now());
        }

        demande.setDossier(dossier.get());
        dossier.get().setDemande(demande);

        return demandeRepository.save(demande);
    }

    @Override
    @Transactional
    public DemandeRemboursement updateDemande(Long id, DemandeRemboursement updatedDemande) {
        DemandeRemboursement demande = demandeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Demande non trouvée avec l'ID : " + id));

        demande.setType(updatedDemande.getType());
        demande.setMontant(updatedDemande.getMontant());
        demande.setStatut(updatedDemande.getStatut());

        return demandeRepository.save(demande);
    }

    @Override
    @Transactional
    public void deleteDemande(Long id) {
        DemandeRemboursement demande = demandeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Demande non trouvée avec l'ID : " + id));

        Dossier dossier = demande.getDossier();
        if (dossier != null) {
            dossier.setDemande(null);
            dossierRepository.save(dossier);
        }

        demandeRepository.deleteById(id);
    }

    @Override
    public DemandeRemboursement updateStatutDemande(Long id, StatutDemande statut) {
        DemandeRemboursement demande = demandeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Demande non trouvée avec l'ID : " + id));
        
        demande.setStatut(statut);
        return demandeRepository.save(demande);
    }
} 