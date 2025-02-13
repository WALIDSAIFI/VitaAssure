package com.assure.vita.Repository;

import com.assure.vita.Entity.MembreFamille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembreFamilleRepository extends JpaRepository<MembreFamille, Long> {
    List<MembreFamille> findByUtilisateurId(Long utilisateurId);
}
