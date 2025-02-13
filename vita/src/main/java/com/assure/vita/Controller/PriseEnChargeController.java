package com.assure.vita.Controller;

import com.assure.vita.DTO.request.PriseEnChargeRequestDTO;
import com.assure.vita.DTO.response.PriseEnChargeResponseDTO;
import com.assure.vita.Entity.PriseEnCharge;
import com.assure.vita.Mapper.PriseEnChargeMapper;
import com.assure.vita.Service.Interface.IPriseEnChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prises-en-charge")
@RequiredArgsConstructor
public class PriseEnChargeController {

    private final IPriseEnChargeService priseEnChargeService;
    private final PriseEnChargeMapper priseEnChargeMapper;

    // Récupérer toutes les prises en charge
    @GetMapping
    public List<PriseEnChargeResponseDTO> getAll() {
        return priseEnChargeService.getAllPrisesEnCharge().stream()
                .map(priseEnChargeMapper::toDto)
                .toList();
    }

    // Récupérer une prise en charge par ID
    @GetMapping("/{id}")
    public ResponseEntity<PriseEnChargeResponseDTO> getById(@PathVariable Long id) {
        return priseEnChargeService.getPriseEnChargeById(id)
                .map(priseEnChargeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer une nouvelle prise en charge
    @PostMapping
    public PriseEnChargeResponseDTO create(@RequestBody PriseEnChargeRequestDTO requestDTO) {
        PriseEnCharge priseEnCharge = priseEnChargeMapper.toEntity(requestDTO);
        return priseEnChargeMapper.toDto(priseEnChargeService.savePriseEnCharge(priseEnCharge));
    }

    // Mettre à jour une prise en charge
    @PutMapping("/{id}")
    public ResponseEntity<PriseEnChargeResponseDTO> update(
            @PathVariable Long id,
            @RequestBody PriseEnChargeRequestDTO requestDTO) {
        PriseEnCharge priseEnCharge = priseEnChargeMapper.toEntity(requestDTO);
        return ResponseEntity.ok(priseEnChargeMapper.toDto(
                priseEnChargeService.updatePriseEnCharge(id, priseEnCharge)));
    }

    // Supprimer une prise en charge
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        priseEnChargeService.deletePriseEnCharge(id);
        return ResponseEntity.noContent().build();
    }

    // Rejeter une prise en charge
    @PostMapping("/{id}/rejeter")
    public PriseEnChargeResponseDTO rejeter(
            @PathVariable Long id,
            @RequestParam String motif) {
        return priseEnChargeMapper.toDto(
                priseEnChargeService.rejeterPriseEnCharge(id, motif));
    }

    // Accepter une prise en charge
    @PostMapping("/{id}/accepter")
    public PriseEnChargeResponseDTO accepter(@PathVariable Long id) {
        return priseEnChargeMapper.toDto(
                priseEnChargeService.accepterPriseEnCharge(id));
    }
} 