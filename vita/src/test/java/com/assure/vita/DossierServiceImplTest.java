package com.assure.vita;

import com.assure.vita.Entity.Dossier;
import com.assure.vita.Enum.StatutDossier;
import com.assure.vita.Exception.BadRequestException;
import com.assure.vita.Exception.ResourceNotFoundException;
import com.assure.vita.Repository.DossierRepository;
import com.assure.vita.Service.Interface.IRapportService;
import com.assure.vita.Service.impl.DossierServiceImpl;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DossierServiceImplTest {

    @Mock
    private DossierRepository dossierRepository;

    @Mock
    private IRapportService rapportService;

    @InjectMocks
    private DossierServiceImpl dossierService;

    private Dossier dossier;
    private final Long dossierId = 1L;
    private final Long utilisateurId = 1L;
    private final Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    void setUp() {
        dossier = new Dossier();
        dossier.setId(dossierId);
        dossier.setStatut(StatutDossier.EN_ATTENTE);
        dossier.setId(utilisateurId);
    }

    @Test
    void getAllDossiers_ShouldReturnPageOfDossiers() {
        // Arrange
        List<Dossier> dossiers = Arrays.asList(dossier, new Dossier());
        Page<Dossier> expectedPage = new PageImpl<>(dossiers);
        when(dossierRepository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<Dossier> result = dossierService.getAllDossiers(pageable);

        // Assert
        assertEquals(2, result.getTotalElements());
        verify(dossierRepository).findAll(pageable);
    }

    @Test
    void getDossierById_WhenDossierExists_ShouldReturnDossier() {
        // Arrange
        when(dossierRepository.findById(dossierId)).thenReturn(Optional.of(dossier));

        // Act
        Optional<Dossier> result = dossierService.getDossierById(dossierId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(dossierId, result.get().getId());
    }

    @Test
    void getDossierById_WhenDossierNotExists_ShouldReturnEmpty() {
        // Arrange
        when(dossierRepository.findById(dossierId)).thenReturn(Optional.empty());

        // Act
        Optional<Dossier> result = dossierService.getDossierById(dossierId);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void getDossiersByUtilisateurId_ShouldReturnPageOfDossiers() {
        // Arrange
        List<Dossier> dossiers = Arrays.asList(dossier);
        Page<Dossier> expectedPage = new PageImpl<>(dossiers);
        when(dossierRepository.findByUtilisateurId(utilisateurId, pageable)).thenReturn(expectedPage);

        // Act
        Page<Dossier> result = dossierService.getDossiersByUtilisateurId(utilisateurId, pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(utilisateurId, result.getContent().get(0).getId());
    }

    @Test
    void getDossiersByStatut_ShouldReturnPageOfDossiers() {
        // Arrange
        List<Dossier> dossiers = Arrays.asList(dossier);
        Page<Dossier> expectedPage = new PageImpl<>(dossiers);
        when(dossierRepository.findByStatut(StatutDossier.EN_ATTENTE, pageable)).thenReturn(expectedPage);

        // Act
        Page<Dossier> result = dossierService.getDossiersByStatut(StatutDossier.EN_ATTENTE, pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(StatutDossier.EN_ATTENTE, result.getContent().get(0).getStatut());
    }

    @Test
    void saveDossier_ShouldSetDefaultStatusAndSave() {
        // Arrange
        Dossier newDossier = new Dossier();
        newDossier.setId(utilisateurId);

        when(dossierRepository.save(any(Dossier.class))).thenAnswer(invocation -> {
            Dossier savedDossier = invocation.getArgument(0);
            savedDossier.setId(dossierId);
            return savedDossier;
        });

        // Act
        Dossier result = dossierService.saveDossier(newDossier);

        // Assert
        assertNotNull(result.getId());
        assertEquals(StatutDossier.EN_ATTENTE, result.getStatut());
        verify(dossierRepository).save(newDossier);
    }

    @Test
    void updateDossier_WhenDossierExists_ShouldUpdateFields() {
        // Arrange
        Dossier updatedDossier = new Dossier();
        updatedDossier.setStatut(StatutDossier.ACCEPTE);
        updatedDossier.setDateTraitement(LocalDate.now());

        when(dossierRepository.findById(dossierId)).thenReturn(Optional.of(dossier));
        when(dossierRepository.save(any(Dossier.class))).thenReturn(dossier);

        // Act
        Dossier result = dossierService.updateDossier(dossierId, updatedDossier);

        // Assert
        assertEquals(StatutDossier.ACCEPTE, result.getStatut());
        assertEquals(LocalDate.now(), result.getDateTraitement());
        verify(dossierRepository).save(dossier);
    }

    @Test
    void updateDossier_WhenDossierNotExists_ShouldThrowException() {
        // Arrange
        Dossier updatedDossier = new Dossier();
        when(dossierRepository.findById(dossierId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            dossierService.updateDossier(dossierId, updatedDossier);
        });
    }

    @Test
    void deleteDossier_WhenDossierExists_ShouldDelete() {
        // Arrange
        when(dossierRepository.existsById(dossierId)).thenReturn(true);

        // Act
        dossierService.deleteDossier(dossierId);

        // Assert
        verify(dossierRepository).deleteById(dossierId);
    }

    @Test
    void deleteDossier_WhenDossierNotExists_ShouldThrowException() {
        // Arrange
        when(dossierRepository.existsById(dossierId)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            dossierService.deleteDossier(dossierId);
        });
    }

    @Test
    void rejeterDossier_WhenDossierExistsAndNotTraite_ShouldReject() {
        // Arrange
        dossier.setStatut(StatutDossier.EN_ATTENTE);
        when(dossierRepository.findById(dossierId)).thenReturn(Optional.of(dossier));
        when(dossierRepository.save(any(Dossier.class))).thenReturn(dossier);

        // Act
        Dossier result = dossierService.rejeterDossier(dossierId);

        // Assert
        assertEquals(StatutDossier.REJETE, result.getStatut());
        assertEquals(LocalDate.now(), result.getDateTraitement());
    }

    @Test
    void rejeterDossier_WhenDossierIsTraite_ShouldThrowException() {
        // Arrange
        dossier.setStatut(StatutDossier.TRAITE);
        when(dossierRepository.findById(dossierId)).thenReturn(Optional.of(dossier));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            dossierService.rejeterDossier(dossierId);
        });
    }

    @Test
    void rejeterDossier_WhenDossierNotExists_ShouldThrowException() {
        // Arrange
        when(dossierRepository.findById(dossierId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            dossierService.rejeterDossier(dossierId);
        });
    }

    @Test
    void accepterDossier_WhenDossierIsEnAttente_ShouldAccept() {
        // Arrange
        dossier.setStatut(StatutDossier.EN_ATTENTE);
        when(dossierRepository.findById(dossierId)).thenReturn(Optional.of(dossier));
        when(dossierRepository.save(any(Dossier.class))).thenReturn(dossier);

        // Act
        Dossier result = dossierService.accepterDossier(dossierId);

        // Assert
        assertEquals(StatutDossier.ACCEPTE, result.getStatut());
        assertEquals(LocalDate.now(), result.getDateTraitement());
    }

    @Test
    void accepterDossier_WhenDossierIsNotEnAttente_ShouldThrowException() {
        // Arrange
        dossier.setStatut(StatutDossier.ACCEPTE);
        when(dossierRepository.findById(dossierId)).thenReturn(Optional.of(dossier));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            dossierService.accepterDossier(dossierId);
        });
    }

    @Test
    void accepterDossier_WhenDossierNotExists_ShouldThrowException() {
        // Arrange
        when(dossierRepository.findById(dossierId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            dossierService.accepterDossier(dossierId);
        });
    }
}