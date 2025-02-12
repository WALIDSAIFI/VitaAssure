package com.assure.vita.DTO.request;

import com.assure.vita.Entity.Role;
import lombok.Data;
import java.util.List;

@Data
public class UtilisateurRequestDTO {
    private String username;
    private String password;
    private String email;
    private Role role;
} 