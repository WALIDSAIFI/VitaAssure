package com.assure.vita.Repository;

import com.assure.vita.Entity.DemandeRemboursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRemboursementRepository extends JpaRepository<DemandeRemboursement, Long> {
    List<DemandeRemboursement> findByDossierId(Long dossierId);
}
