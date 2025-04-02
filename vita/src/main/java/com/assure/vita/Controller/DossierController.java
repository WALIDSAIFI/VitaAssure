package com.assure.vita.Controller;

import com.assure.vita.DTO.request.DossierRequestDTO;
import com.assure.vita.DTO.response.DossierResponseDTO;
import com.assure.vita.Entity.Dossier;
import com.assure.vita.Enum.StatutDossier;
import com.assure.vita.Exception.ResourceNotFoundException;
import com.assure.vita.Service.Interface.IDossierService;
import com.assure.vita.Mapper.DossierMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/dossiers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DossierController {

    private final IDossierService dossierService;
    private final DossierMapper dossierMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Page<DossierResponseDTO>> getAllDossiers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Dossier> dossierPage = dossierService.getAllDossiers(pageable);
        
        Page<DossierResponseDTO> responsePage = dossierPage.map(dossierMapper::toDto);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    @PreAuthorize("hasRole('ADHERENT') or @securityService.isCurrentUser(#utilisateurId)")
    public ResponseEntity<Page<DossierResponseDTO>> getDossiersByUtilisateur(
            @PathVariable Long utilisateurId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Dossier> dossierPage = dossierService.getDossiersByUtilisateurId(utilisateurId, pageable);
        
        Page<DossierResponseDTO> responsePage = dossierPage.map(dossierMapper::toDto);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/statut/{statut}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Page<DossierResponseDTO>> getDossiersByStatut(
            @PathVariable StatutDossier statut,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Dossier> dossierPage = dossierService.getDossiersByStatut(statut, pageable);
        
        Page<DossierResponseDTO> responsePage = dossierPage.map(dossierMapper::toDto);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADHERENT') or @securityService.isDossierOwner(#id)")
    public ResponseEntity<DossierResponseDTO> getDossierById(
            @PathVariable Long id) {
        Dossier dossier = dossierService.getDossierById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Dossier non trouvé avec l'ID : " + id));
        return ResponseEntity.ok(dossierMapper.toDto(dossier));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADHERENT')")
    public ResponseEntity<DossierResponseDTO> createDossier(
            @Valid @RequestBody DossierRequestDTO dossierDTO) {
        Dossier dossier = dossierMapper.toEntity(dossierDTO);
        Dossier savedDossier = dossierService.saveDossier(dossier);
        return ResponseEntity.ok(dossierMapper.toDto(savedDossier));
    }

    @PutMapping("/accepter/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<DossierResponseDTO> accepterDossier(@PathVariable Long id) {
        Dossier dossier = dossierService.accepterDossier(id);
        return ResponseEntity.ok(dossierMapper.toDto(dossier));
    }

    @PutMapping("/rejeter/{id}")
    public ResponseEntity<DossierResponseDTO> rejeterDossier(
            @PathVariable Long id
            ) {
        Dossier dossier = dossierService.rejeterDossier(id);
        return ResponseEntity.ok(dossierMapper.toDto(dossier));
    }
} 