package com.assure.vita.Controller;

import com.assure.vita.DTO.request.DemandeRemboursementRequestDTO;
import com.assure.vita.DTO.response.DemandeRemboursementResponseDTO;
import com.assure.vita.Entity.DemandeRemboursement;
import com.assure.vita.Enum.StatutDemande;
import com.assure.vita.Exception.ResourceNotFoundException;
import com.assure.vita.Mapper.DemandeRemboursementMapper;
import com.assure.vita.Service.Interface.IDemandeRemboursementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/demandes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DemandeRemboursementController {

    private final IDemandeRemboursementService demandeService;
    private final DemandeRemboursementMapper demandeMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Page<DemandeRemboursementResponseDTO>> getAllDemandes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DemandeRemboursement> demandePage = demandeService.getAllDemandes(pageable);
        return ResponseEntity.ok(demandePage.map(demandeMapper::toDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR') or @securityService.isDemandeOwner(#id)")
    public ResponseEntity<DemandeRemboursementResponseDTO> getDemandeById(@PathVariable Long id) {
        DemandeRemboursement demande = demandeService.getDemandeById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Demande non trouvée avec l'ID : " + id));
        return ResponseEntity.ok(demandeMapper.toDto(demande));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADHERENT')")
    public ResponseEntity<DemandeRemboursementResponseDTO> createDemande(
            @Valid @RequestBody DemandeRemboursementRequestDTO requestDTO) {
        DemandeRemboursement demande = demandeMapper.toEntity(requestDTO);
        demande = demandeService.saveDemande(demande);
        return ResponseEntity.ok(demandeMapper.toDto(demande));
    }

    @PutMapping("/{id}/approuver")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<DemandeRemboursementResponseDTO> approuverDemande(@PathVariable Long id) {
        DemandeRemboursement demande = demandeService.updateStatutDemande(id, StatutDemande.APPROUVE);
        return ResponseEntity.ok(demandeMapper.toDto(demande));
    }

    @PutMapping("/{id}/rejeter")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<DemandeRemboursementResponseDTO> rejeterDemande(
            @PathVariable Long id,
            @RequestParam String motif) {
        DemandeRemboursement demande = demandeService.rejeterDemande(id, motif);
        return ResponseEntity.ok(demandeMapper.toDto(demande));
    }

    @GetMapping("/dossier/{dossierId}")
    public ResponseEntity<Page<DemandeRemboursementResponseDTO>> getDemandesByDossier(
            @PathVariable Long dossierId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DemandeRemboursement> demandePage = demandeService.getDemandesByDossier(dossierId, pageable);
        return ResponseEntity.ok(demandePage.map(demandeMapper::toDto));
    }
} 