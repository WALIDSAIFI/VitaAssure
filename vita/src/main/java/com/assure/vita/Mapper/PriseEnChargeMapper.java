package com.assure.vita.Mapper;

import com.assure.vita.Entity.PriseEnCharge;
import com.assure.vita.DTO.request.PriseEnChargeRequestDTO;
import com.assure.vita.DTO.response.PriseEnChargeResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriseEnChargeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "utilisateur.id", source = "utilisateurId")
    PriseEnCharge toEntity(PriseEnChargeRequestDTO requestDTO);

    @Mapping(source = "utilisateur.id", target = "utilisateurId")
    PriseEnChargeResponseDTO toDto(PriseEnCharge priseEnCharge);
} 