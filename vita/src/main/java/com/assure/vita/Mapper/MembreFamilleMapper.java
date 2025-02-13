package com.assure.vita.Mapper;

import com.assure.vita.Entity.MembreFamille;
import com.assure.vita.DTO.request.MembreFamilleRequestDTO;
import com.assure.vita.DTO.response.MembreFamilleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MembreFamilleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "utilisateur.id", source = "userId")
    MembreFamille toEntity(MembreFamilleRequestDTO requestDTO);

    @Mapping(source = "utilisateur.id", target = "assureId")
    MembreFamilleResponseDTO toDto(MembreFamille membreFamille);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "utilisateur.id", source = "userId")
    void updateEntity(MembreFamilleRequestDTO requestDTO, @MappingTarget MembreFamille entity);
}
