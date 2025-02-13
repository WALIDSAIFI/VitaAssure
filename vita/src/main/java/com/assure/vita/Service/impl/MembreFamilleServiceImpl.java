package com.assure.vita.Service.impl;

import com.assure.vita.Entity.MembreFamille;
import com.assure.vita.Exception.ResourceNotFoundException;
import com.assure.vita.Repository.MembreFamilleRepository;
import com.assure.vita.Service.Interface.IMembreFamilleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MembreFamilleServiceImpl implements IMembreFamilleService {

    private final MembreFamilleRepository membreFamilleRepository;

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
        if (!membreFamilleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Membre de famille non trouvé avec l'ID : " + id);
        }
        membreFamilleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public MembreFamille updateMembreFamille(Long id, MembreFamille updatedMembreFamille) {
        MembreFamille membreFamille = membreFamilleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Membre de famille non trouvé avec l'ID : " + id));

        membreFamille.setNom(updatedMembreFamille.getNom());
        membreFamille.setPrenom(updatedMembreFamille.getPrenom());
        membreFamille.setDateNaissance(updatedMembreFamille.getDateNaissance());
        membreFamille.setLienParente(updatedMembreFamille.getLienParente());

        return membreFamilleRepository.save(membreFamille);
    }
} 