package com.assure.vita.Mapper;

import com.assure.vita.Entity.Role;
import com.assure.vita.DTO.request.RoleRequestDTO;
import com.assure.vita.DTO.response.RoleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "utilisateurs", ignore = true)
    Role toEntity(RoleRequestDTO requestDTO);

    RoleResponseDTO toDto(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "utilisateurs", ignore = true)
    void updateEntity(RoleRequestDTO requestDTO, @MappingTarget Role role);
} 