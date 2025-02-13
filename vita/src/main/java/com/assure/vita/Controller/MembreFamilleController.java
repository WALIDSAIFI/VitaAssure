package com.assure.vita.Controller;

import com.assure.vita.DTO.request.MembreFamilleRequestDTO;
import com.assure.vita.DTO.response.MembreFamilleResponseDTO;
import com.assure.vita.Entity.MembreFamille;
import com.assure.vita.Mapper.MembreFamilleMapper;
import com.assure.vita.Service.Interface.IMembreFamilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/membres-famille")
@CrossOrigin(origins = "*")
public class MembreFamilleController {

    private final IMembreFamilleService membreFamilleService;
    private final MembreFamilleMapper membreFamilleMapper;

    @Autowired
    public MembreFamilleController(IMembreFamilleService membreFamilleService, 
                                 MembreFamilleMapper membreFamilleMapper) {
        this.membreFamilleService = membreFamilleService;
        this.membreFamilleMapper = membreFamilleMapper;
    }

    @GetMapping
    public ResponseEntity<List<MembreFamilleResponseDTO>> getAllMembresFamille() {
        List<MembreFamille> membres = membreFamilleService.getAllMembresFamille();
        List<MembreFamilleResponseDTO> membresDTO = membres.stream()
                .map(membreFamilleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(membresDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembreFamilleResponseDTO> getMembreFamilleById(@PathVariable Long id) {
        return membreFamilleService.getMembreFamilleById(id)
                .map(membre -> ResponseEntity.ok(membreFamilleMapper.toDto(membre)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<MembreFamilleResponseDTO>> getMembresFamilleByUtilisateurId(
            @PathVariable Long utilisateurId) {
        List<MembreFamille> membres = membreFamilleService.getMembresFamilleByUtilisateurId(utilisateurId);
        List<MembreFamilleResponseDTO> membresDTO = membres.stream()
                .map(membreFamilleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(membresDTO);
    }

    @PostMapping
    public ResponseEntity<MembreFamilleResponseDTO> createMembreFamille(
            @RequestBody MembreFamilleRequestDTO requestDTO) {
        MembreFamille membre = membreFamilleMapper.toEntity(requestDTO);
        MembreFamille savedMembre = membreFamilleService.saveMembreFamille(membre);
        return ResponseEntity.ok(membreFamilleMapper.toDto(savedMembre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembreFamilleResponseDTO> updateMembreFamille(
            @PathVariable Long id,
            @RequestBody MembreFamilleRequestDTO requestDTO) {
        return membreFamilleService.getMembreFamilleById(id)
                .map(existingMembre -> {
                    membreFamilleMapper.updateEntity(requestDTO, existingMembre);
                    MembreFamille updatedMembre = membreFamilleService.saveMembreFamille(existingMembre);
                    return ResponseEntity.ok(membreFamilleMapper.toDto(updatedMembre));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembreFamille(@PathVariable Long id) {
        membreFamilleService.deleteMembreFamille(id);
        return ResponseEntity.ok().build();
    }
} 