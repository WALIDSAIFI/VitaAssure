package com.assure.vita.Controller;

import com.assure.vita.DTO.request.UtilisateurUpdateDTO;
import com.assure.vita.DTO.response.UtilisateurResponseDTO;
import com.assure.vita.Entity.Utilisateur;
import com.assure.vita.Exception.ResourceNotFoundException;
import com.assure.vita.Mapper.UtilisateurMapper;
import com.assure.vita.Service.Interface.IAuthenticationService;
import com.assure.vita.Service.Interface.IUtilisateurService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@Slf4j
public class UtilisateurController {

    private final IUtilisateurService utilisateurService;
    private final UtilisateurMapper utilisateurMapper;
    private final IAuthenticationService authService;
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Page<UtilisateurResponseDTO>> getAllUtilisateurs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Utilisateur> utilisateurPage = utilisateurService.getAllUtilisateurs(pageable);
        return ResponseEntity.ok(utilisateurPage.map(utilisateurMapper::toDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR') or @securityService.isCurrentUser(#id)")
    public ResponseEntity<UtilisateurResponseDTO> getUtilisateurById(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        return ResponseEntity.ok(utilisateurMapper.toDto(utilisateur));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR') or hasRole('ADHERENT') or @securityService.isCurrentUser(#id)")
    public ResponseEntity<UtilisateurResponseDTO> updateUtilisateur(
            @PathVariable Long id,
            @Valid @RequestBody UtilisateurUpdateDTO updateDTO) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        utilisateurMapper.updateUtilisateur(updateDTO, utilisateur);
        utilisateur = utilisateurService.updateUtilisateur(id, utilisateur);
        return ResponseEntity.ok(utilisateurMapper.toDto(utilisateur));
    }


    @PutMapping("/{id}/valider")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<UtilisateurResponseDTO> validerUtilisateur(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.validerUtilisateur(id);
        return ResponseEntity.ok(utilisateurMapper.toDto(utilisateur));
    }

    @PutMapping("/{id}/bloquer")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<UtilisateurResponseDTO> bloquerUtilisateur(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.bloquerUtilisateur(id);
        return ResponseEntity.ok(utilisateurMapper.toDto(utilisateur));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Page<UtilisateurResponseDTO>> searchUtilisateurs(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Utilisateur> utilisateurPage = utilisateurService.searchUtilisateurs(nom, email, pageable);
        return ResponseEntity.ok(utilisateurPage.map(utilisateurMapper::toDto));
    }


    @PutMapping("/validate/{userId}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<String> validateUser(@PathVariable Long userId) {
        authService.validateUser(userId);
        return ResponseEntity.ok("Compte utilisateur validé avec succès");
    }

} 