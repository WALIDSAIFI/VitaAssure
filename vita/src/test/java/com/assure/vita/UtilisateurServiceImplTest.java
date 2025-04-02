package com.assure.vita;

import com.assure.vita.DTO.response.UtilisateurResponseDTO;
import com.assure.vita.Entity.Utilisateur;
import com.assure.vita.Enum.Role;
import com.assure.vita.Enum.SituationFamiliale;
import com.assure.vita.Exception.BadRequestException;
import com.assure.vita.Exception.ResourceNotFoundException;
import com.assure.vita.Mapper.UtilisateurMapper;
import com.assure.vita.Repository.UtilisateurRepository;
import com.assure.vita.Service.impl.UtilisateurServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtilisateurServiceImplTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private UtilisateurMapper utilisateurMapper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UtilisateurServiceImpl utilisateurService;

    private Utilisateur utilisateur;
    private UtilisateurResponseDTO utilisateurResponseDTO;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setNom("Doe");
        utilisateur.setPrenom("John");
        utilisateur.setEmail("john.doe@example.com");
        utilisateur.setDateNaissance(LocalDate.of(1990, 1, 1));
        utilisateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        utilisateur.setRole(Role.ADHERENT);
        utilisateur.setValider(true);

        utilisateurResponseDTO = new UtilisateurResponseDTO();
        utilisateurResponseDTO.setId(1L);
        utilisateurResponseDTO.setNom("Doe");
        utilisateurResponseDTO.setPrenom("John");
        utilisateurResponseDTO.setEmail("john.doe@example.com");
    }

    @Test
    void getAllUtilisateurs_ShouldReturnPageOfUtilisateurs() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Utilisateur> utilisateurs = Collections.singletonList(utilisateur);
        Page<Utilisateur> expectedPage = new PageImpl<>(utilisateurs, pageable, 1);

        when(utilisateurRepository.findByRole(Role.ADHERENT, pageable)).thenReturn(expectedPage);

        // Act
        Page<Utilisateur> result = utilisateurService.getAllUtilisateurs(pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(utilisateur, result.getContent().get(0));
        verify(utilisateurRepository).findByRole(Role.ADHERENT, pageable);
    }

    @Test
    void getUtilisateurById_WhenExists_ShouldReturnUtilisateur() {
        // Arrange
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));

        // Act
        Optional<Utilisateur> result = utilisateurService.getUtilisateurById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(utilisateur, result.get());
        verify(utilisateurRepository).findById(1L);
    }

    @Test
    void getUtilisateurById_WhenNotExists_ShouldReturnEmpty() {
        // Arrange
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Utilisateur> result = utilisateurService.getUtilisateurById(1L);

        // Assert
        assertTrue(result.isEmpty());
        verify(utilisateurRepository).findById(1L);
    }

    @Test
    void getUtilisateurByEmail_WhenValidEmail_ShouldReturnUtilisateur() {
        // Arrange
        when(utilisateurRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(utilisateur));

        // Act
        Optional<Utilisateur> result = utilisateurService.getUtilisateurByEmail("john.doe@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(utilisateur, result.get());
        verify(utilisateurRepository).findByEmail("john.doe@example.com");
    }

    @Test
    void getUtilisateurByEmail_WhenEmptyEmail_ShouldThrowException() {
        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            utilisateurService.getUtilisateurByEmail("");
        });
        verify(utilisateurRepository, never()).findByEmail(anyString());
    }

    @Test
    void saveUtilisateur_WhenValid_ShouldSaveAndReturnUtilisateur() {
        // Arrange
        when(utilisateurRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        // Act
        Utilisateur result = utilisateurService.saveUtilisateur(utilisateur);

        // Assert
        assertNotNull(result);
        assertEquals(utilisateur, result);
        verify(utilisateurRepository).findByEmail(utilisateur.getEmail());
        verify(utilisateurRepository).save(utilisateur);
    }

    @Test
    void saveUtilisateur_WhenEmailExists_ShouldThrowException() {
        // Arrange
        when(utilisateurRepository.findByEmail(anyString())).thenReturn(Optional.of(utilisateur));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            utilisateurService.saveUtilisateur(utilisateur);
        });
        verify(utilisateurRepository).findByEmail(utilisateur.getEmail());
        verify(utilisateurRepository, never()).save(any());
    }

    @Test
    void saveUtilisateur_WhenInvalidData_ShouldThrowException() {
        // Arrange
        Utilisateur invalidUtilisateur = new Utilisateur();

        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            utilisateurService.saveUtilisateur(invalidUtilisateur);
        });
        verify(utilisateurRepository, never()).findByEmail(anyString());
        verify(utilisateurRepository, never()).save(any());
    }

    @Test
    void updateUtilisateur_WhenValid_ShouldUpdateAndReturnUtilisateur() {
        // Arrange
        Utilisateur updatedUtilisateur = new Utilisateur();
        updatedUtilisateur.setNom("Updated");
        updatedUtilisateur.setPrenom("User");
        updatedUtilisateur.setEmail("updated@example.com");
        updatedUtilisateur.setDateNaissance(LocalDate.now());
        updatedUtilisateur.setSituationFamiliale(SituationFamiliale.MARIE);

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(utilisateurRepository.findByEmail("updated@example.com")).thenReturn(Optional.empty());
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        // Act
        Utilisateur result = utilisateurService.updateUtilisateur(1L, updatedUtilisateur);

        // Assert
        assertNotNull(result);
        verify(utilisateurRepository).findById(1L);
        verify(utilisateurRepository).findByEmail("updated@example.com");
        verify(utilisateurRepository).save(utilisateur);
    }

    @Test
    void updateUtilisateur_WhenEmailExistsForOtherUser_ShouldThrowException() {
        // Arrange
        Utilisateur existingUserWithEmail = new Utilisateur();
        existingUserWithEmail.setId(2L);
        existingUserWithEmail.setEmail("existing@example.com");

        Utilisateur updatedUtilisateur = new Utilisateur();
        updatedUtilisateur.setEmail("existing@example.com");

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(utilisateurRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(existingUserWithEmail));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            utilisateurService.updateUtilisateur(1L, updatedUtilisateur);
        });
        verify(utilisateurRepository).findById(1L);
        verify(utilisateurRepository).findByEmail("existing@example.com");
        verify(utilisateurRepository, never()).save(any());
    }

    @Test
    void deleteUtilisateur_WhenExists_ShouldDelete() {
        // Arrange
        when(utilisateurRepository.existsById(1L)).thenReturn(true);

        // Act
        utilisateurService.deleteUtilisateur(1L);

        // Assert
        verify(utilisateurRepository).existsById(1L);
        verify(utilisateurRepository).deleteById(1L);
    }

    @Test
    void deleteUtilisateur_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(utilisateurRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            utilisateurService.deleteUtilisateur(1L);
        });
        verify(utilisateurRepository).existsById(1L);
        verify(utilisateurRepository, never()).deleteById(1L);
    }

    @Test
    void searchUtilisateurs_ShouldReturnFilteredResults() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Utilisateur> utilisateurs = Collections.singletonList(utilisateur);
        Page<Utilisateur> expectedPage = new PageImpl<>(utilisateurs, pageable, 1);

        when(utilisateurRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(expectedPage);

        // Act
        Page<Utilisateur> result = utilisateurService.searchUtilisateurs("Doe", "john.doe@example.com", pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        verify(utilisateurRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void validerUtilisateur_ShouldSetValiderToTrue() {
        // Arrange
        utilisateur.setValider(false);
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        // Act
        Utilisateur result = utilisateurService.validerUtilisateur(1L);


        verify(utilisateurRepository).findById(1L);
        verify(utilisateurRepository).save(utilisateur);
    }

    @Test
    void bloquerUtilisateur_ShouldSetValiderToFalse() {
        // Arrange
        utilisateur.setValider(true);
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        // Act
        Utilisateur result = utilisateurService.bloquerUtilisateur(1L);


        verify(utilisateurRepository).findById(1L);
        verify(utilisateurRepository).save(utilisateur);
    }

    @Test
    void getUserByUsername_WhenExists_ShouldReturnDTO() {
        // Arrange
        when(utilisateurRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(utilisateur));
        when(utilisateurMapper.toDto(utilisateur)).thenReturn(utilisateurResponseDTO);

        // Act
        UtilisateurResponseDTO result = utilisateurService.getUserByUsername("john.doe@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(utilisateurResponseDTO, result);
        verify(utilisateurRepository).findByEmail("john.doe@example.com");
        verify(utilisateurMapper).toDto(utilisateur);
    }

    @Test
    void getUserByUsername_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(utilisateurRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            utilisateurService.getUserByUsername("unknown@example.com");
        });
        verify(utilisateurRepository).findByEmail("unknown@example.com");
        verify(utilisateurMapper, never()).toDto(any());
    }

    @Test
    void getCurrentUser_ShouldReturnCurrentUserDTO() {
        // Arrange
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("john.doe@example.com");
        when(utilisateurRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(utilisateur));
        when(utilisateurMapper.toDto(utilisateur)).thenReturn(utilisateurResponseDTO);

        // Act
        UtilisateurResponseDTO result = utilisateurService.getCurrentUser();

        // Assert
        assertNotNull(result);
        assertEquals(utilisateurResponseDTO, result);
        verify(securityContext).getAuthentication();
        verify(authentication).getName();
        verify(utilisateurRepository).findByEmail("john.doe@example.com");
        verify(utilisateurMapper).toDto(utilisateur);
    }
}