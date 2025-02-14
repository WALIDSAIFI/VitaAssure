package com.assure.vita.Service.impl;

import com.assure.vita.Entity.Dossier;
import com.assure.vita.Enum.StatutDossier;
import com.assure.vita.Entity.Rapport;
import com.assure.vita.Exception.BadRequestException;
import com.assure.vita.Exception.ResourceNotFoundException;
import com.assure.vita.Repository.DossierRepository;
import com.assure.vita.Service.Interface.IDossierService;
import com.assure.vita.Service.Interface.IRapportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DossierServiceImpl implements IDossierService {
    
    private final DossierRepository dossierRepository;
    private final IRapportService rapportService;

    @Override
    public Page<Dossier> getAllDossiers(Pageable pageable) {
        return dossierRepository.findAll(pageable);
    }

    @Override
    public Optional<Dossier> getDossierById(Long id) {
        return dossierRepository.findById(id);
    }

    @Override
    public Page<Dossier> getDossiersByUtilisateurId(Long utilisateurId, Pageable pageable) {
        return dossierRepository.findByUtilisateurId(utilisateurId, pageable);
    }

    @Override
    public Page<Dossier> getDossiersByStatut(StatutDossier statut, Pageable pageable) {
        return dossierRepository.findByStatut(statut, pageable);
    }

    @Override
    @Transactional
    public Dossier saveDossier(Dossier dossier) {
        if (dossier.getStatut() == null) {
            dossier.setStatut(StatutDossier.EN_ATTENTE);
        }
        return dossierRepository.save(dossier);
    }

    @Override
    @Transactional
    public Dossier updateDossier(Long id, Dossier updatedDossier) {
        Dossier dossier = dossierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Dossier non trouvé avec l'ID : " + id));

        if (updatedDossier.getStatut() != null) {
            dossier.setStatut(updatedDossier.getStatut());
        }
        if (updatedDossier.getDateTraitement() != null) {
            dossier.setDateTraitement(updatedDossier.getDateTraitement());
        }

        return dossierRepository.save(dossier);
    }

    @Override
    @Transactional
    public void deleteDossier(Long id) {
        if (!dossierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Dossier non trouvé avec l'ID : " + id);
        }
        dossierRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Dossier rejeterDossier(Long id, String motifRejet) {
        Dossier dossier = dossierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Dossier non trouvé avec l'ID : " + id));

        if (dossier.getStatut() == StatutDossier.TRAITE) {
            throw new BadRequestException("Impossible de rejeter un dossier déjà traité");
        }

        // Créer un rapport de rejet
        Rapport rapport = new Rapport();
        rapport.setDemandeRemboursement(dossier.getDemande());
        rapport.setDetails("Motif de rejet : " + motifRejet);
        rapport.setDateRapport(LocalDate.now());
        rapportService.saveRapport(rapport);

        // Mettre à jour le statut du dossier
        dossier.setStatut(StatutDossier.REJETE);
        dossier.setDateTraitement(LocalDate.now());

        return dossierRepository.save(dossier);
    }

    @Override
    @Transactional
    public Dossier accepterDossier(Long id) {
        Dossier dossier = dossierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Dossier non trouvé avec l'ID : " + id));

        if (dossier.getStatut() != StatutDossier.EN_ATTENTE) {
            throw new BadRequestException("Ce dossier ne peut plus être accepté");
        }

        dossier.setStatut(StatutDossier.TRAITE);
        dossier.setDateTraitement(LocalDate.now());

        return dossierRepository.save(dossier);
    }
} 