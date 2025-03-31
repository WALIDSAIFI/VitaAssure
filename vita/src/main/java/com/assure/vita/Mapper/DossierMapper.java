package com.assure.vita.Mapper;

import com.assure.vita.Entity.Dossier;
import com.assure.vita.DTO.request.DossierRequestDTO;
import com.assure.vita.DTO.response.DossierResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DossierMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "utilisateur.id", source = "utilisateurId")
    @Mapping(target = "totalFrais", source = "totalFrais")
    Dossier toEntity(DossierRequestDTO requestDTO);

    @Mapping(source = "utilisateur.id", target = "utilisateurId")
    @Mapping(source = "totalFrais", target = "totalFrais")
    DossierResponseDTO toDto(Dossier dossier);
} 