package com.assure.vita.Service.Interface;

import com.assure.vita.Entity.PriseEnCharge;
import com.assure.vita.Enum.StatutPriseEnCharge;
import java.util.List;
import java.util.Optional;

public interface IPriseEnChargeService {
    List<PriseEnCharge> getAllPrisesEnCharge();
    Optional<PriseEnCharge> getPriseEnChargeById(Long id);
    List<PriseEnCharge> getPriseEnChargeByUtilisateur(Long utilisateurId);
    List<PriseEnCharge> getPriseEnChargeByStatut(StatutPriseEnCharge statut);
    PriseEnCharge savePriseEnCharge(PriseEnCharge priseEnCharge);
    PriseEnCharge updatePriseEnCharge(Long id, PriseEnCharge priseEnCharge);
    void deletePriseEnCharge(Long id);
    PriseEnCharge rejeterPriseEnCharge(Long id);
    PriseEnCharge accepterPriseEnCharge(Long id);
} 