package com.assure.vita.DTO.request;

import lombok.Data;
import java.util.List;

@Data
public class UtilisateurRequestDTO {
    private String username;
    private String password;
    private String email;
    private List<Long> roleIds;
} 