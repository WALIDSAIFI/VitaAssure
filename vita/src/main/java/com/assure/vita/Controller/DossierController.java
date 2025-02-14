package com.assure.vita.Controller;

import com.assure.vita.DTO.response.DossierResponseDTO;
import com.assure.vita.Entity.Dossier;
import com.assure.vita.Enum.StatutDossier;
import com.assure.vita.Service.Interface.IDossierService;
import com.assure.vita.Mapper.DossierMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dossiers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DossierController {

    private final IDossierService dossierService;
    private final DossierMapper dossierMapper;

    @GetMapping
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
    public ResponseEntity<Page<DossierResponseDTO>> getDossiersByStatut(
            @PathVariable StatutDossier statut,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Dossier> dossierPage = dossierService.getDossiersByStatut(statut, pageable);
        
        Page<DossierResponseDTO> responsePage = dossierPage.map(dossierMapper::toDto);
        return ResponseEntity.ok(responsePage);
    }
} 