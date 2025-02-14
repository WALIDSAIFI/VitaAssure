package com.assure.vita.Repository;

import com.assure.vita.Entity.Dossier;
import com.assure.vita.Enum.StatutDossier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DossierRepository extends JpaRepository<Dossier, Long> {
    Page<Dossier> findAll(Pageable pageable);
    Page<Dossier> findByUtilisateurId(Long utilisateurId, Pageable pageable);
    Page<Dossier> findByStatut(StatutDossier statut, Pageable pageable);
    List<Dossier> findByUtilisateurId(Long utilisateurId);
}
