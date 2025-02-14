package com.assure.vita.Repository;

import com.assure.vita.Entity.DemandeRemboursement;
import com.assure.vita.Enum.StatutDemande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRemboursementRepository extends JpaRepository<DemandeRemboursement, Long> {
    Page<DemandeRemboursement> findAll(Pageable pageable);
    Page<DemandeRemboursement> findByDossierId(Long dossierId, Pageable pageable);
    Page<DemandeRemboursement> findByStatut(StatutDemande statut, Pageable pageable);
    List<DemandeRemboursement> findByDossierId(Long dossierId);
}
