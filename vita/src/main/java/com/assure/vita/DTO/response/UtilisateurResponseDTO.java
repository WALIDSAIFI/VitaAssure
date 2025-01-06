package com.assure.vita.DTO.response;

import lombok.Data;
import java.util.List;

@Data
public class UtilisateurResponseDTO {
    private Long id;
    private String username;
    private String email;
    private List<RoleResponseDTO> roles;
} 