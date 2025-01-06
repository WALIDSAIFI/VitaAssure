package com.assure.vita.Service;

import com.assure.vita.DTO.request.RoleRequestDTO;
import com.assure.vita.DTO.response.RoleResponseDTO;
import java.util.List;

public interface RoleService {
    RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO);
    RoleResponseDTO getRoleById(Long id);
    List<RoleResponseDTO> getAllRoles();
    RoleResponseDTO updateRole(Long id, RoleRequestDTO roleRequestDTO);
    void deleteRole(Long id);
} 