package com.assure.vita.Service.impl;

import com.assure.vita.DTO.request.LoginRequestDTO;
import com.assure.vita.DTO.request.RegisterRequestDTO;
import com.assure.vita.DTO.response.AuthResponseDTO;
import com.assure.vita.Entity.Utilisateur;
import com.assure.vita.Exception.BadRequestException;
import com.assure.vita.Repository.UtilisateurRepository;
import com.assure.vita.Security.JwtTokenProvider;
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
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (utilisateurRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email déjà utilisé");
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
        utilisateur.setValider(false);

        utilisateur = utilisateurRepository.save(utilisateur);

        return new AuthResponseDTO(
            utilisateur.getId(),
            utilisateur.getEmail(),
            null,
            "Inscription réussie"
        );
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = tokenProvider.generateToken(authentication);
        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BadRequestException("Utilisateur non trouvé"));

        return new AuthResponseDTO(
            utilisateur.getId(),
            utilisateur.getEmail(),
            token,
            "Connexion réussie"
        );
    }
} 