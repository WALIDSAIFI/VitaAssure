package com.assure.vita.Service.impl;

import com.assure.vita.DTO.request.LoginRequestDTO;
import com.assure.vita.DTO.request.RegisterRequestDTO;
import com.assure.vita.DTO.response.AuthResponseDTO;
import com.assure.vita.Entity.Utilisateur;
import com.assure.vita.Enum.Role;
import com.assure.vita.Exception.ResourceNotFoundException;
import com.assure.vita.Repository.UtilisateurRepository;
import com.assure.vita.Security.JwtService;
import com.assure.vita.Service.Interface.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (utilisateurRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        utilisateur.setDateNaissance(request.getDateNaissance());
        utilisateur.setAdresse(request.getAdresse());
        utilisateur.setTelephone(request.getTelephone());
        utilisateur.setSituationFamiliale(request.getSituationFamiliale());
        utilisateur.setRole(Role.ADHERENT);
        utilisateur.setValider(false);

        utilisateur = utilisateurRepository.save(utilisateur);

        String token = jwtService.generateToken(utilisateur);

        return new AuthResponseDTO(
            utilisateur.getId(),
            utilisateur.getEmail(),
            token,
            "Inscription réussie. En attente de validation par un administrateur."
        );
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!utilisateur.getValider()) {
            throw new RuntimeException("Compte non validé. Veuillez attendre la validation par un administrateur.");
        }

        String token = jwtService.generateToken(utilisateur);

        return new AuthResponseDTO(
            utilisateur.getId(),
            utilisateur.getEmail(),
            token,
            "Connexion réussie"
        );
    }

    @Override
    @Transactional
    public void validateUser(Long userId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + userId));

        if (utilisateur.getValider()) {
            throw new RuntimeException("Ce compte est déjà validé");
        }

        utilisateur.setValider(true);
        utilisateurRepository.save(utilisateur);
    }

} 