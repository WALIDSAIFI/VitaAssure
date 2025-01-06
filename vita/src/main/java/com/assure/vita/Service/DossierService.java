package com.assure.vita.Service;

import com.assure.vita.DTO.request.DossierRequestDTO;
import com.assure.vita.DTO.response.DossierResponseDTO;
import java.util.List;

public interface DossierService {
    DossierResponseDTO createDossier(DossierRequestDTO dossierRequestDTO);
    DossierResponseDTO getDossierById(Long id);
    List<DossierResponseDTO> getAllDossiers();
    List<DossierResponseDTO> getDossiersByAssureId(Long assureId);
    DossierResponseDTO updateDossier(Long id, DossierRequestDTO dossierRequestDTO);
    void deleteDossier(Long id);
} 