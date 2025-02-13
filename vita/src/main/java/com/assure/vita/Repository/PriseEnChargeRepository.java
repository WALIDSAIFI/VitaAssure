package com.assure.vita.Repository;

import com.assure.vita.Entity.PriseEnCharge;
import com.assure.vita.Entity.StatutPriseEnCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PriseEnChargeRepository extends JpaRepository<PriseEnCharge, Long> {
    List<PriseEnCharge> findByUtilisateurId(Long utilisateurId);
    List<PriseEnCharge> findByStatut(StatutPriseEnCharge statut);
} 