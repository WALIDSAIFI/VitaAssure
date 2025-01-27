package com.assure.vita.Service.impl;

import com.assure.vita.Entity.Role;
import com.assure.vita.Entity.Utilisateur;
import com.assure.vita.DTO.request.UtilisateurRequestDTO;
import com.assure.vita.DTO.response.UtilisateurResponseDTO;
import com.assure.vita.Mapper.UtilisateurMapper;
import com.assure.vita.Repository.RoleRepository;
import com.assure.vita.Repository.UtilisateurRepository;
import com.assure.vita.Service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final UtilisateurMapper utilisateurMapper;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, 
                                 RoleRepository roleRepository,
                                 UtilisateurMapper utilisateurMapper) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.utilisateurMapper = utilisateurMapper;
    }

    @Override
    public UtilisateurResponseDTO createUtilisateur(UtilisateurRequestDTO utilisateurRequestDTO) {
        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurRequestDTO);
        
        // Gérer les rôles
        if (utilisateurRequestDTO.getRoleIds() != null) {
            List<Role> roles = utilisateurRequestDTO.getRoleIds().stream()
                    .map(roleId -> roleRepository.findById(roleId)
                            .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId)))
                    .collect(Collectors.toList());
            utilisateur.setRoles(roles);
        }
        
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(savedUtilisateur);
    }

    @Override
    public UtilisateurResponseDTO getUtilisateurById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur not found with id: " + id));
        return utilisateurMapper.toDto(utilisateur);
    }

    @Override
    public List<UtilisateurResponseDTO> getAllUtilisateurs() {
        return utilisateurRepository.findAll().stream()
                .map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UtilisateurResponseDTO updateUtilisateur(Long id, UtilisateurRequestDTO utilisateurRequestDTO) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur not found with id: " + id));
        
        utilisateurMapper.updateEntity(utilisateurRequestDTO, utilisateur);
        
        // Mettre à jour les rôles
        if (utilisateurRequestDTO.getRoleIds() != null) {
            List<Role> roles = utilisateurRequestDTO.getRoleIds().stream()
                    .map(roleId -> roleRepository.findById(roleId)
                            .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId)))
                    .collect(Collectors.toList());
            utilisateur.setRoles(roles);
        }
        
        Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(updatedUtilisateur);
    }

    @Override
    public void deleteUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur not found with id: " + id);
        }
        utilisateurRepository.deleteById(id);
    }
} 