package com.assure.vita.Service.Interface;

import com.assure.vita.Entity.Rapport;
import java.util.List;
import java.util.Optional;

public interface IRapportService {
    List<Rapport> getAllRapports();
    Optional<Rapport> getRapportById(Long id);
    List<Rapport> getRapportsByDossierId(Long dossierId);
    Rapport saveRapport(Rapport rapport);
    Rapport updateRapport(Long id, Rapport rapport);
    void deleteRapport(Long id);
} 