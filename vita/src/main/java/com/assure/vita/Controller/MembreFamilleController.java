package com.assure.vita.Controller;

import com.assure.vita.DTO.request.MembreFamilleRequestDTO;
import com.assure.vita.DTO.response.MembreFamilleResponseDTO;
import com.assure.vita.Entity.MembreFamille;
import com.assure.vita.Mapper.MembreFamilleMapper;
import com.assure.vita.Service.Interface.IMembreFamilleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/membres-famille")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MembreFamilleController {

    private final IMembreFamilleService membreFamilleService;
    private final MembreFamilleMapper membreFamilleMapper;

    @GetMapping
    public ResponseEntity<List<MembreFamilleResponseDTO>> getAllMembresFamille() {
        List<MembreFamille> membresFamille = membreFamilleService.getAllMembresFamille();
        List<MembreFamilleResponseDTO> responseList = membresFamille.stream()
                .map(membreFamilleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembreFamilleResponseDTO> getMembreFamilleById(@PathVariable Long id) {
        return membreFamilleService.getMembreFamilleById(id)
                .map(membreFamilleMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/utilisateur/{userId}")
    public ResponseEntity<List<MembreFamilleResponseDTO>> getMembresFamilleByUtilisateur(@PathVariable Long userId) {
        List<MembreFamille> membresFamille = membreFamilleService.getMembresFamilleByUtilisateurId(userId);
        List<MembreFamilleResponseDTO> responseList = membresFamille.stream()
                .map(membreFamilleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @PostMapping
    public ResponseEntity<MembreFamilleResponseDTO> createMembreFamille(@RequestBody MembreFamilleRequestDTO requestDTO) {
        MembreFamille membreFamille = membreFamilleMapper.toEntity(requestDTO);
        MembreFamille savedMembreFamille = membreFamilleService.saveMembreFamille(membreFamille);
        return new ResponseEntity<>(membreFamilleMapper.toDto(savedMembreFamille), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembreFamilleResponseDTO> updateMembreFamille(
            @PathVariable Long id,
            @RequestBody MembreFamilleRequestDTO requestDTO) {
        MembreFamille membreFamille = membreFamilleMapper.toEntity(requestDTO);
        MembreFamille updatedMembreFamille = membreFamilleService.updateMembreFamille(id, membreFamille);
        return ResponseEntity.ok(membreFamilleMapper.toDto(updatedMembreFamille));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembreFamille(@PathVariable Long id) {
        membreFamilleService.deleteMembreFamille(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MembreFamilleResponseDTO> partialUpdateMembreFamille(
            @PathVariable Long id,
            @RequestBody MembreFamilleRequestDTO requestDTO) {
        membreFamilleService.getMembreFamilleById(id)
                .orElseThrow(() -> new RuntimeException("Membre de famille non trouvé avec l'ID : " + id));

        MembreFamille membreFamille = membreFamilleMapper.toEntity(requestDTO);
        membreFamilleMapper.updateEntity(requestDTO, membreFamille);
        
        MembreFamille updatedMembreFamille = membreFamilleService.updateMembreFamille(id, membreFamille);
        return ResponseEntity.ok(membreFamilleMapper.toDto(updatedMembreFamille));
    }
} 