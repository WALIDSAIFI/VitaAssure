package com.assure.vita.Repository;

import com.assure.vita.Entity.Assure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssureRepository extends JpaRepository<Assure, Long> {
    // Méthodes personnalisées si nécessaire
} 