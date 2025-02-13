package com.assure.vita.Repository;

import com.assure.vita.Entity.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DossierRepository extends JpaRepository<Dossier, Long> {
    List<Dossier> findByUtilisateurId(Long utilisateurId);
}
