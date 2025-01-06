package com.assure.vita.Service.impl;

import com.assure.vita.Entity.Assure;
import com.assure.vita.DTO.request.AssureRequestDTO;
import com.assure.vita.DTO.response.AssureResponseDTO;
import com.assure.vita.Mapper.AssureMapper;
import com.assure.vita.Repository.AssureRepository;
import com.assure.vita.Service.AssureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssureServiceImpl implements AssureService {

    private final AssureRepository assureRepository;
    private final AssureMapper assureMapper;

    @Autowired
    public AssureServiceImpl(AssureRepository assureRepository, AssureMapper assureMapper) {
        this.assureRepository = assureRepository;
        this.assureMapper = assureMapper;
    }

    @Override
    public AssureResponseDTO createAssure(AssureRequestDTO assureRequestDTO) {
        Assure assure = assureMapper.toEntity(assureRequestDTO);
        Assure savedAssure = assureRepository.save(assure);
        return assureMapper.toDto(savedAssure);
    }

    @Override
    public AssureResponseDTO getAssureById(Long id) {
        Assure assure = assureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assuré non trouvé avec l'id: " + id));
        return assureMapper.toDto(assure);
    }

    @Override
    public List<AssureResponseDTO> getAllAssures() {
        return assureRepository.findAll().stream()
                .map(assureMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AssureResponseDTO updateAssure(Long id, AssureRequestDTO assureRequestDTO) {
        Assure assure = assureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assuré non trouvé avec l'id: " + id));
        assureMapper.updateEntity(assureRequestDTO, assure);
        Assure updatedAssure = assureRepository.save(assure);
        return assureMapper.toDto(updatedAssure);
    }

    @Override
    public void deleteAssure(Long id) {
        if (!assureRepository.existsById(id)) {
            throw new RuntimeException("Assuré non trouvé avec l'id: " + id);
        }
        assureRepository.deleteById(id);
    }
} 