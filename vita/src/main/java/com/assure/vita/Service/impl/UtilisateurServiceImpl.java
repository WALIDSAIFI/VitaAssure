package com.assure.vita.Service.impl;

import com.assure.vita.DTO.response.UtilisateurResponseDTO;
import com.assure.vita.Entity.Utilisateur;
import com.assure.vita.Enum.Role;
import com.assure.vita.Exception.ResourceNotFoundException;
import com.assure.vita.Exception.BadRequestException;
import com.assure.vita.Mapper.UtilisateurMapper;
import com.assure.vita.Repository.UtilisateurRepository;
import com.assure.vita.Service.Interface.IUtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UtilisateurServiceImpl implements IUtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private  final UtilisateurMapper utilisateurMapper;

    @Override
    public Page<Utilisateur> getAllUtilisateurs(Pageable pageable) {
        return utilisateurRepository.findByRole(Role.ADHERENT, pageable);
    }


    @Override
    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    @Override
    public Optional<Utilisateur> getUtilisateurByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new BadRequestException("L'email ne peut pas être vide");
        }
        return utilisateurRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        // Validation des données
        validateUtilisateur(utilisateur);
        
        // Vérifier si l'email existe déjà
        if (utilisateur.getId() == null && 
            utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
            throw new BadRequestException("Un utilisateur avec cet email existe déjà");
        }

        return utilisateurRepository.save(utilisateur);
    }

    @Override
    @Transactional
    public Utilisateur updateUtilisateur(Long id, Utilisateur updatedUtilisateur) {
        Utilisateur existingUtilisateur = utilisateurRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));

        // Validation des données
        validateUtilisateur(updatedUtilisateur);

        // Vérifier si le nouvel email n'est pas déjà utilisé par un autre utilisateur
        Optional<Utilisateur> userWithEmail = utilisateurRepository.findByEmail(updatedUtilisateur.getEmail());
        if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(id)) {
            throw new BadRequestException("Cet email est déjà utilisé par un autre utilisateur");
        }

        // Mise à jour des champs
        existingUtilisateur.setNom(updatedUtilisateur.getNom());
        existingUtilisateur.setPrenom(updatedUtilisateur.getPrenom());
        existingUtilisateur.setDateNaissance(updatedUtilisateur.getDateNaissance());
        existingUtilisateur.setAdresse(updatedUtilisateur.getAdresse());
        existingUtilisateur.setTelephone(updatedUtilisateur.getTelephone());
        existingUtilisateur.setEmail(updatedUtilisateur.getEmail());
        existingUtilisateur.setSituationFamiliale(updatedUtilisateur.getSituationFamiliale());

        return utilisateurRepository.save(existingUtilisateur);
    }

    @Override
    @Transactional
    public void deleteUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public Page<Utilisateur> searchUtilisateurs(String nom, String email, Pageable pageable) {
        Specification<Utilisateur> spec = Specification.where(null);
        
        if (nom != null && !nom.isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("nom")), "%" + nom.toLowerCase() + "%"));
        }
        
        if (email != null && !email.isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }
        
        return utilisateurRepository.findAll(spec, pageable);
    }

    @Override
    public Utilisateur validerUtilisateur(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        utilisateur.setValider(true);
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur bloquerUtilisateur(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        utilisateur.setValider(false);
        return utilisateurRepository.save(utilisateur);
    }

    private void validateUtilisateur(Utilisateur utilisateur) {
        if (utilisateur.getNom() == null || utilisateur.getNom().trim().isEmpty()) {
            throw new BadRequestException("Le nom est obligatoire");
        }
        if (utilisateur.getPrenom() == null || utilisateur.getPrenom().trim().isEmpty()) {
            throw new BadRequestException("Le prénom est obligatoire");
        }
        if (utilisateur.getEmail() == null || utilisateur.getEmail().trim().isEmpty()) {
            throw new BadRequestException("L'email est obligatoire");
        }
        if (utilisateur.getDateNaissance() == null) {
            throw new BadRequestException("La date de naissance est obligatoire");
        }
        if (utilisateur.getSituationFamiliale() == null) {
            throw new BadRequestException("La situation familiale est obligatoire");
        }
    }



    public UtilisateurResponseDTO getUserByUsername(String username) {
        Utilisateur user = utilisateurRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return utilisateurMapper.toDto(user);
    }


    @Override
    public UtilisateurResponseDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentUsername = authentication.getName();
       // log.info("Current user: {}", currentUsername);
        return getUserByUsername(currentUsername);
    }
} 