package com.assure.vita.Controller;

import com.assure.vita.DTO.request.AssureRequestDTO;
import com.assure.vita.DTO.response.AssureResponseDTO;
import com.assure.vita.Service.AssureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assures")
public class AssureController {

    private final AssureService assureService;

    @Autowired
    public AssureController(AssureService assureService) {
        this.assureService = assureService;
    }

    @PostMapping
    public ResponseEntity<AssureResponseDTO> createAssure(@RequestBody AssureRequestDTO assureRequestDTO) {
        AssureResponseDTO createdAssure = assureService.createAssure(assureRequestDTO);
        return new ResponseEntity<>(createdAssure, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssureResponseDTO> getAssureById(@PathVariable Long id) {
        AssureResponseDTO assure = assureService.getAssureById(id);
        return ResponseEntity.ok(assure);
    }

    @GetMapping
    public ResponseEntity<List<AssureResponseDTO>> getAllAssures() {
        List<AssureResponseDTO> assures = assureService.getAllAssures();
        return ResponseEntity.ok(assures);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssureResponseDTO> updateAssure(
            @PathVariable Long id,
            @RequestBody AssureRequestDTO assureRequestDTO) {
        AssureResponseDTO updatedAssure = assureService.updateAssure(id, assureRequestDTO);
        return ResponseEntity.ok(updatedAssure);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssure(@PathVariable Long id) {
        assureService.deleteAssure(id);
        return ResponseEntity.noContent().build();
    }
} 