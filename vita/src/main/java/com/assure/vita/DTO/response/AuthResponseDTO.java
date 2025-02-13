package com.assure.vita.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private Long userId;
    private String email;
    private String token;
    private String message;
} 