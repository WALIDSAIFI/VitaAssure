package com.assure.vita.Service;

import com.assure.vita.DTO.request.AssureRequestDTO;
import com.assure.vita.DTO.response.AssureResponseDTO;
import java.util.List;

public interface AssureService {
    AssureResponseDTO createAssure(AssureRequestDTO assureRequestDTO);
    AssureResponseDTO getAssureById(Long id);
    List<AssureResponseDTO> getAllAssures();
    AssureResponseDTO updateAssure(Long id, AssureRequestDTO assureRequestDTO);
    void deleteAssure(Long id);
} 