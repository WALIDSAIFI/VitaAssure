package com.assure.vita.Service.impl;

import com.assure.vita.Entity.PriseEnCharge;
import com.assure.vita.Entity.StatutPriseEnCharge;
import com.assure.vita.Entity.Rapport;
import com.assure.vita.Exception.BadRequestException;
import com.assure.vita.Exception.ResourceNotFoundException;
import com.assure.vita.Repository.PriseEnChargeRepository;
import com.assure.vita.Service.Interface.IPriseEnChargeService;
import com.assure.vita.Service.Interface.IRapportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PriseEnChargeServiceImpl implements IPriseEnChargeService {

    private final PriseEnChargeRepository priseEnChargeRepository;
    private final IRapportService rapportService;

    @Override
    public List<PriseEnCharge> getAllPrisesEnCharge() {
        return priseEnChargeRepository.findAll();
    }

    @Override
    public Optional<PriseEnCharge> getPriseEnChargeById(Long id) {
        return priseEnChargeRepository.findById(id);
    }

    @Override
    public List<PriseEnCharge> getPriseEnChargeByUtilisateur(Long utilisateurId) {
        return priseEnChargeRepository.findByUtilisateurId(utilisateurId);
    }

    @Override
    public List<PriseEnCharge> getPriseEnChargeByStatut(StatutPriseEnCharge statut) {
        return priseEnChargeRepository.findByStatut(statut);
    }

    @Override
    @Transactional
    public PriseEnCharge savePriseEnCharge(PriseEnCharge priseEnCharge) {
        if (priseEnCharge.getDateDemande() == null) {
            priseEnCharge.setDateDemande(LocalDate.now());
        }
        if (priseEnCharge.getStatut() == null) {
            priseEnCharge.setStatut(StatutPriseEnCharge.EN_ATTENTE);
        }
        return priseEnChargeRepository.save(priseEnCharge);
    }

    @Override
    @Transactional
    public PriseEnCharge updatePriseEnCharge(Long id, PriseEnCharge updatedPriseEnCharge) {
        PriseEnCharge priseEnCharge = priseEnChargeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Prise en charge non trouvée avec l'ID : " + id));

        priseEnCharge.setDescription(updatedPriseEnCharge.getDescription());
        priseEnCharge.setMontantEstime(updatedPriseEnCharge.getMontantEstime());
        priseEnCharge.setStatut(updatedPriseEnCharge.getStatut());
        priseEnCharge.setCommentaire(updatedPriseEnCharge.getCommentaire());

        return priseEnChargeRepository.save(priseEnCharge);
    }

    @Override
    @Transactional
    public void deletePriseEnCharge(Long id) {
        if (!priseEnChargeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Prise en charge non trouvée avec l'ID : " + id);
        }
        priseEnChargeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PriseEnCharge rejeterPriseEnCharge(Long id, String motifRejet) {
        PriseEnCharge priseEnCharge = priseEnChargeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Prise en charge non trouvée avec l'ID : " + id));

        if (priseEnCharge.getStatut() != StatutPriseEnCharge.EN_ATTENTE) {
            throw new BadRequestException("Cette prise en charge ne peut plus être rejetée");
        }

        // Créer un rapport de rejet
        Rapport rapport = new Rapport();
        rapport.setPriseEnCharge(priseEnCharge);
        rapport.setDetails("Motif de rejet : " + motifRejet);
        rapport.setDateRapport(LocalDate.now());
        rapportService.saveRapport(rapport);

        // Mettre à jour le statut
        priseEnCharge.setStatut(StatutPriseEnCharge.REFUSEE);

        return priseEnChargeRepository.save(priseEnCharge);
    }

    @Override
    @Transactional
    public PriseEnCharge accepterPriseEnCharge(Long id) {
        PriseEnCharge priseEnCharge = priseEnChargeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Prise en charge non trouvée avec l'ID : " + id));

        if (priseEnCharge.getStatut() != StatutPriseEnCharge.EN_ATTENTE) {
            throw new BadRequestException("Cette prise en charge ne peut plus être acceptée");
        }

        priseEnCharge.setStatut(StatutPriseEnCharge.ACCEPTEE);

        return priseEnChargeRepository.save(priseEnCharge);
    }
} 