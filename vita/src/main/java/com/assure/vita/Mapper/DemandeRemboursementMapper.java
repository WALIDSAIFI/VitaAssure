package com.assure.vita.Mapper;

import com.assure.vita.Entity.DemandeRemboursement;
import com.assure.vita.DTO.request.DemandeRemboursementRequestDTO;
import com.assure.vita.DTO.response.DemandeRemboursementResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DemandeRemboursementMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dossier.id", source = "dossierId")
    DemandeRemboursement toEntity(DemandeRemboursementRequestDTO requestDTO);

    @Mapping(source = "dossier.id", target = "dossierId")
    DemandeRemboursementResponseDTO toDto(DemandeRemboursement demande);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dossier", ignore = true)
    void updateEntity(DemandeRemboursementRequestDTO requestDTO, @MappingTarget DemandeRemboursement entity);
} 