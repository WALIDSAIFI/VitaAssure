package com.assure.vita.Mapper;

import com.assure.vita.Entity.Dossier;
import com.assure.vita.DTO.request.DossierRequestDTO;
import com.assure.vita.DTO.response.DossierResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {DemandeRemboursementMapper.class})
public interface DossierMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assure", ignore = true)
    @Mapping(target = "demandes", ignore = true)
    Dossier toEntity(DossierRequestDTO requestDTO);

    @Mapping(source = "assure.id", target = "assureId")
    DossierResponseDTO toDto(Dossier dossier);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assure", ignore = true)
    @Mapping(target = "demandes", ignore = true)
    void updateEntity(DossierRequestDTO requestDTO, @MappingTarget Dossier dossier);
} 