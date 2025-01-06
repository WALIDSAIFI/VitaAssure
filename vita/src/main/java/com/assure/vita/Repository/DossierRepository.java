package com.assure.vita.Repository;

import com.assure.vita.Entity.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DossierRepository extends JpaRepository<Dossier, Long> {
    
    // Trouver tous les dossiers d'un assuré
    List<Dossier> findByAssureId(Long assureId);
    
    // Trouver les dossiers par statut
    List<Dossier> findByStatut(String statut);
    
    // Trouver les dossiers par assuré et statut
    List<Dossier> findByAssureIdAndStatut(Long assureId, String statut);
    
    // Vérifier si un assuré a des dossiers
    boolean existsByAssureId(Long assureId);
} 