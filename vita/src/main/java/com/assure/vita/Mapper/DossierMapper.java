package com.assure.vita.Mapper;

import com.assure.vita.Entity.Dossier;
import com.assure.vita.Entity.Utilisateur;
import com.assure.vita.DTO.request.DossierRequestDTO;
import com.assure.vita.DTO.response.DossierResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {DemandeRemboursementMapper.class})
public abstract class DossierMapper {

    @Autowired
    protected DemandeRemboursementMapper demandeRemboursementMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "utilisateur", expression = "java(createUtilisateur(requestDTO.getUtilisateurId()))")
    @Mapping(target = "demande", ignore = true)
    public abstract Dossier toEntity(DossierRequestDTO requestDTO);

    @Mapping(source = "utilisateur.id", target = "utilisateurId")
    @Mapping(target = "demandes", expression = "java(dossier.getDemande() != null ? java.util.Collections.singletonList(demandeRemboursementMapper.toDto(dossier.getDemande())) : java.util.Collections.emptyList())")
    public abstract DossierResponseDTO toDto(Dossier dossier);

    protected Utilisateur createUtilisateur(Long id) {
        if (id == null) return null;
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(id);
        return utilisateur;
    }
} 