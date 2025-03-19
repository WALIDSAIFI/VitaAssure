package com.assure.vita.Controller;

import com.assure.vita.DTO.request.LoginRequestDTO;
import com.assure.vita.DTO.request.RegisterRequestDTO;
import com.assure.vita.DTO.response.AuthResponseDTO;
import com.assure.vita.DTO.response.UtilisateurResponseDTO;
import com.assure.vita.Service.Interface.IAuthenticationService;
import com.assure.vita.Service.Interface.IUtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class AuthController {

    private final IAuthenticationService authService;
    private final IUtilisateurService utilisateurService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }


    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")

    public ResponseEntity<UtilisateurResponseDTO> getCurrentUser() {
        log.info("Fetching current user profile");
        UtilisateurResponseDTO user = utilisateurService.getCurrentUser();
        return ResponseEntity.ok(user);
    }

} 