package com.assure.vita.Service.impl;

import com.assure.vita.Entity.DemandeRemboursement;
import com.assure.vita.Entity.Dossier;
import com.assure.vita.Enum.StatutDemande;
import com.assure.vita.Exception.ResourceNotFoundException;
import com.assure.vita.Exception.BadRequestException;
import com.assure.vita.Repository.DemandeRemboursementRepository;
import com.assure.vita.Repository.DossierRepository;
import com.assure.vita.Service.Interface.IDemandeRemboursementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DemandeRemboursementServiceImpl implements IDemandeRemboursementService {

    private final DemandeRemboursementRepository demandeRepository;
    private final DossierRepository dossierRepository;

    @Override
    public Page<DemandeRemboursement> getAllDemandes(Pageable pageable) {
        return demandeRepository.findAll(pageable);
    }

    @Override
    public Optional<DemandeRemboursement> getDemandeById(Long id) {
        return demandeRepository.findById(id);
    }

    @Override
    public Page<DemandeRemboursement> getDemandesByDossier(Long dossierId, Pageable pageable) {
        return demandeRepository.findByDossierId(dossierId, pageable);
    }

    @Override
    public List<DemandeRemboursement> getDemandesByDossierId(Long dossierId) {
        return demandeRepository.findByDossierId(dossierId);
    }

    @Override
    public DemandeRemboursement saveDemande(DemandeRemboursement demande) {
        demande.setDateDemande(LocalDate.now());
        demande.setStatut(StatutDemande.EN_ATTENTE);
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
        demande.setDateTraitement(LocalDate.now());
        return demandeRepository.save(demande);
    }

    @Override
    public DemandeRemboursement rejeterDemande(Long id, String motif) {
        DemandeRemboursement demande = demandeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Demande non trouvée avec l'ID : " + id));
        
        demande.setStatut(StatutDemande.REJETE);
        demande.setCommentaire(motif);
        demande.setDateTraitement(LocalDate.now());
        return demandeRepository.save(demande);
    }
} 