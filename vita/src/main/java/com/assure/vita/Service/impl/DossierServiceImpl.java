package com.assure.vita.Service.impl;

import com.assure.vita.Entity.Dossier;
import com.assure.vita.Entity.Assure;
import com.assure.vita.DTO.request.DossierRequestDTO;
import com.assure.vita.DTO.response.DossierResponseDTO;
import com.assure.vita.Mapper.DossierMapper;
import com.assure.vita.Repository.DossierRepository;
import com.assure.vita.Repository.AssureRepository;
import com.assure.vita.Service.DossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DossierServiceImpl implements DossierService {

    private final DossierRepository dossierRepository;
    private final AssureRepository assureRepository;
    private final DossierMapper dossierMapper;

    @Autowired
    public DossierServiceImpl(DossierRepository dossierRepository, 
                            AssureRepository assureRepository,
                            DossierMapper dossierMapper) {
        this.dossierRepository = dossierRepository;
        this.assureRepository = assureRepository;
        this.dossierMapper = dossierMapper;
    }

    @Override
    public DossierResponseDTO createDossier(DossierRequestDTO dossierRequestDTO) {
        Dossier dossier = dossierMapper.toEntity(dossierRequestDTO);
        
        Assure assure = assureRepository.findById(dossierRequestDTO.getAssureId())
                .orElseThrow(() -> new RuntimeException("Assure not found"));
        
        dossier.setAssure(assure);
        Dossier savedDossier = dossierRepository.save(dossier);
        return dossierMapper.toDto(savedDossier);
    }

    @Override
    public DossierResponseDTO getDossierById(Long id) {
        Dossier dossier = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier non trouvé"));
        return dossierMapper.toDto(dossier);
    }

    @Override
    public List<DossierResponseDTO> getAllDossiers() {
        return dossierRepository.findAll().stream()
                .map(dossierMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DossierResponseDTO> getDossiersByAssureId(Long assureId) {
        return dossierRepository.findByAssureId(assureId).stream()
                .map(dossierMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DossierResponseDTO updateDossier(Long id, DossierRequestDTO dossierRequestDTO) {
        Dossier dossier = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier non trouvé"));
        
        dossierMapper.updateEntity(dossierRequestDTO, dossier);
        
        if (dossierRequestDTO.getAssureId() != null) {
            Assure assure = assureRepository.findById(dossierRequestDTO.getAssureId())
                    .orElseThrow(() -> new RuntimeException("Assuré non trouvé"));
            dossier.setAssure(assure);
        }
        
        Dossier updatedDossier = dossierRepository.save(dossier);
        return dossierMapper.toDto(updatedDossier);
    }

    @Override
    public void deleteDossier(Long id) {
        if (!dossierRepository.existsById(id)) {
            throw new RuntimeException("Dossier non trouvé");
        }
        dossierRepository.deleteById(id);
    }
} 