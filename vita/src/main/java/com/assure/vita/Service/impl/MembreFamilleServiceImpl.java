package com.assure.vita.Service.impl;

import com.assure.vita.Entity.MembreFamille;
import com.assure.vita.Repository.MembreFamilleRepository;
import com.assure.vita.Service.Interface.IMembreFamilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MembreFamilleServiceImpl implements IMembreFamilleService {

    private final MembreFamilleRepository membreFamilleRepository;

    @Autowired
    public MembreFamilleServiceImpl(MembreFamilleRepository membreFamilleRepository) {
        this.membreFamilleRepository = membreFamilleRepository;
    }

    @Override
    public List<MembreFamille> getAllMembresFamille() {
        return membreFamilleRepository.findAll();
    }

    @Override
    public Optional<MembreFamille> getMembreFamilleById(Long id) {
        return membreFamilleRepository.findById(id);
    }

    @Override
    public List<MembreFamille> getMembresFamilleByUtilisateurId(Long utilisateurId) {
        return membreFamilleRepository.findByUtilisateurId(utilisateurId);
    }

    @Override
    public MembreFamille saveMembreFamille(MembreFamille membreFamille) {
        return membreFamilleRepository.save(membreFamille);
    }

    @Override
    public void deleteMembreFamille(Long id) {
        membreFamilleRepository.deleteById(id);
    }
} 