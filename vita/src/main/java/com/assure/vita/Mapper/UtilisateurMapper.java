package com.assure.vita.Mapper;

import com.assure.vita.Entity.Utilisateur;
import com.assure.vita.DTO.request.UtilisateurRequestDTO;
import com.assure.vita.DTO.response.UtilisateurResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", source = "role")
    Utilisateur toEntity(UtilisateurRequestDTO requestDTO);

    UtilisateurResponseDTO toDto(Utilisateur utilisateur);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", source = "role")
    void updateEntity(UtilisateurRequestDTO requestDTO, @MappingTarget Utilisateur utilisateur);
} 