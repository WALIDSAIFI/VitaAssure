package com.assure.vita.Controller;

import com.assure.vita.DTO.request.DossierRequestDTO;
import com.assure.vita.DTO.response.DossierResponseDTO;
import com.assure.vita.Service.DossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dossiers")
public class DossierController {

    private final DossierService dossierService;

    @Autowired
    public DossierController(DossierService dossierService) {
        this.dossierService = dossierService;
    }

    @PostMapping
    public ResponseEntity<DossierResponseDTO> createDossier(@RequestBody DossierRequestDTO dossierRequestDTO) {
        DossierResponseDTO createdDossier = dossierService.createDossier(dossierRequestDTO);
        return new ResponseEntity<>(createdDossier, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DossierResponseDTO> getDossierById(@PathVariable Long id) {
        DossierResponseDTO dossier = dossierService.getDossierById(id);
        return ResponseEntity.ok(dossier);
    }

    @GetMapping
    public ResponseEntity<List<DossierResponseDTO>> getAllDossiers() {
        List<DossierResponseDTO> dossiers = dossierService.getAllDossiers();
        return ResponseEntity.ok(dossiers);
    }

    @GetMapping("/assure/{assureId}")
    public ResponseEntity<List<DossierResponseDTO>> getDossiersByAssureId(@PathVariable Long assureId) {
        List<DossierResponseDTO> dossiers = dossierService.getDossiersByAssureId(assureId);
        return ResponseEntity.ok(dossiers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DossierResponseDTO> updateDossier(
            @PathVariable Long id,
            @RequestBody DossierRequestDTO dossierRequestDTO) {
        DossierResponseDTO updatedDossier = dossierService.updateDossier(id, dossierRequestDTO);
        return ResponseEntity.ok(updatedDossier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDossier(@PathVariable Long id) {
        dossierService.deleteDossier(id);
        return ResponseEntity.noContent().build();
    }
} 