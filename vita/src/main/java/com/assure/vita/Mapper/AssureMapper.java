package com.assure.vita.Mapper;

import com.assure.vita.Entity.Assure;
import com.assure.vita.DTO.request.AssureRequestDTO;
import com.assure.vita.DTO.response.AssureResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {DossierMapper.class})
public interface AssureMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dossiers", ignore = true)
    Assure toEntity(AssureRequestDTO requestDTO);

    AssureResponseDTO toDto(Assure assure);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dossiers", ignore = true)
    void updateEntity(AssureRequestDTO requestDTO, @MappingTarget Assure assure);
} 