package com.assure.vita.Repository;

import com.assure.vita.Entity.MembreFamille;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MembreFamilleRepository extends JpaRepository<MembreFamille, Long> {
    List<MembreFamille> findByAssureId(Long assureId);
}
