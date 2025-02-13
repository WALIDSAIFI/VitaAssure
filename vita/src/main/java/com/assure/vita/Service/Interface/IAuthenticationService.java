package com.assure.vita.Service.Interface;

import com.assure.vita.DTO.request.LoginRequestDTO;
import com.assure.vita.DTO.request.RegisterRequestDTO;
import com.assure.vita.DTO.response.AuthResponseDTO;

public interface IAuthenticationService {
    AuthResponseDTO register(RegisterRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);
} 