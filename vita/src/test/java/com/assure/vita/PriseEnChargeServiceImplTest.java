package com.assure.vita;

import com.assure.vita.Entity.PriseEnCharge;
import com.assure.vita.Enum.StatutPriseEnCharge;
import com.assure.vita.Exception.BadRequestException;
import com.assure.vita.Exception.ResourceNotFoundException;
import com.assure.vita.Repository.PriseEnChargeRepository;
import com.assure.vita.Service.impl.PriseEnChargeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriseEnChargeServiceImplTest {

    @Mock
    private PriseEnChargeRepository priseEnChargeRepository;

    @InjectMocks
    private PriseEnChargeServiceImpl priseEnChargeService;

    private PriseEnCharge priseEnCharge;
    private PriseEnCharge savedPriseEnCharge;

    @BeforeEach
    void setUp() {
        priseEnCharge = new PriseEnCharge();
        priseEnCharge.setDescription("Test description");
        priseEnCharge.setMontantEstime(1000.0);

        savedPriseEnCharge = new PriseEnCharge();
        savedPriseEnCharge.setId(1L);
        savedPriseEnCharge.setDescription("Test description");
        savedPriseEnCharge.setMontantEstime(1000.0);
        savedPriseEnCharge.setStatut(StatutPriseEnCharge.EN_ATTENTE);
        savedPriseEnCharge.setDateDemande(LocalDate.now());
    }

    @Test
    void getAllPrisesEnCharge_ShouldReturnAllPrisesEnCharge() {
        // Arrange
        List<PriseEnCharge> expectedList = Arrays.asList(savedPriseEnCharge);
        when(priseEnChargeRepository.findAll()).thenReturn(expectedList);

        // Act
        List<PriseEnCharge> result = priseEnChargeService.getAllPrisesEnCharge();

        // Assert
        assertEquals(1, result.size());
        assertEquals(savedPriseEnCharge, result.get(0));
        verify(priseEnChargeRepository).findAll();
    }

    @Test
    void getPriseEnChargeById_WhenExists_ShouldReturnPriseEnCharge() {
        // Arrange
        when(priseEnChargeRepository.findById(1L)).thenReturn(Optional.of(savedPriseEnCharge));

        // Act
        Optional<PriseEnCharge> result = priseEnChargeService.getPriseEnChargeById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(savedPriseEnCharge, result.get());
        verify(priseEnChargeRepository).findById(1L);
    }

    @Test
    void getPriseEnChargeById_WhenNotExists_ShouldReturnEmpty() {
        // Arrange
        when(priseEnChargeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<PriseEnCharge> result = priseEnChargeService.getPriseEnChargeById(1L);

        // Assert
        assertTrue(result.isEmpty());
        verify(priseEnChargeRepository).findById(1L);
    }

    @Test
    void getPriseEnChargeByUtilisateur_ShouldReturnList() {
        // Arrange
        List<PriseEnCharge> expectedList = Arrays.asList(savedPriseEnCharge);
        when(priseEnChargeRepository.findByUtilisateurId(1L)).thenReturn(expectedList);

        // Act
        List<PriseEnCharge> result = priseEnChargeService.getPriseEnChargeByUtilisateur(1L);

        // Assert
        assertEquals(1, result.size());
        assertEquals(savedPriseEnCharge, result.get(0));
        verify(priseEnChargeRepository).findByUtilisateurId(1L);
    }

    @Test
    void getPriseEnChargeByStatut_ShouldReturnList() {
        // Arrange
        List<PriseEnCharge> expectedList = Arrays.asList(savedPriseEnCharge);
        when(priseEnChargeRepository.findByStatut(StatutPriseEnCharge.EN_ATTENTE)).thenReturn(expectedList);

        // Act
        List<PriseEnCharge> result = priseEnChargeService.getPriseEnChargeByStatut(StatutPriseEnCharge.EN_ATTENTE);

        // Assert
        assertEquals(1, result.size());
        assertEquals(savedPriseEnCharge, result.get(0));
        verify(priseEnChargeRepository).findByStatut(StatutPriseEnCharge.EN_ATTENTE);
    }

    @Test
    void savePriseEnCharge_ShouldSetDefaultValuesAndSave() {
        // Arrange
        when(priseEnChargeRepository.save(any(PriseEnCharge.class))).thenReturn(savedPriseEnCharge);

        // Act
        PriseEnCharge result = priseEnChargeService.savePriseEnCharge(priseEnCharge);

        // Assert
        assertNotNull(result.getDateDemande());
        assertEquals(StatutPriseEnCharge.EN_ATTENTE, result.getStatut());
        verify(priseEnChargeRepository).save(priseEnCharge);
    }

    @Test
    void updatePriseEnCharge_WhenExists_ShouldUpdate() {
        // Arrange
        PriseEnCharge updated = new PriseEnCharge();
        updated.setDescription("Updated description");
        updated.setMontantEstime(2000.0);
        updated.setStatut(StatutPriseEnCharge.ACCEPTEE);
        updated.setCommentaire("Approved");

        when(priseEnChargeRepository.findById(1L)).thenReturn(Optional.of(savedPriseEnCharge));
        when(priseEnChargeRepository.save(any(PriseEnCharge.class))).thenReturn(savedPriseEnCharge);

        // Act
        PriseEnCharge result = priseEnChargeService.updatePriseEnCharge(1L, updated);

        // Assert
        assertEquals("Updated description", result.getDescription());
        assertEquals(2000.0, result.getMontantEstime());
        assertEquals(StatutPriseEnCharge.ACCEPTEE, result.getStatut());
        assertEquals("Approved", result.getCommentaire());
        verify(priseEnChargeRepository).findById(1L);
        verify(priseEnChargeRepository).save(savedPriseEnCharge);
    }

    @Test
    void updatePriseEnCharge_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(priseEnChargeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            priseEnChargeService.updatePriseEnCharge(1L, new PriseEnCharge());
        });
        verify(priseEnChargeRepository).findById(1L);
        verify(priseEnChargeRepository, never()).save(any());
    }

    @Test
    void deletePriseEnCharge_WhenExists_ShouldDelete() {
        // Arrange
        when(priseEnChargeRepository.existsById(1L)).thenReturn(true);

        // Act
        priseEnChargeService.deletePriseEnCharge(1L);

        // Assert
        verify(priseEnChargeRepository).existsById(1L);
        verify(priseEnChargeRepository).deleteById(1L);
    }

    @Test
    void deletePriseEnCharge_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(priseEnChargeRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            priseEnChargeService.deletePriseEnCharge(1L);
        });
        verify(priseEnChargeRepository).existsById(1L);
        verify(priseEnChargeRepository, never()).deleteById(1L);
    }

    @Test
    void rejeterPriseEnCharge_WhenPending_ShouldReject() {
        // Arrange
        savedPriseEnCharge.setStatut(StatutPriseEnCharge.EN_ATTENTE);
        when(priseEnChargeRepository.findById(1L)).thenReturn(Optional.of(savedPriseEnCharge));
        when(priseEnChargeRepository.save(any(PriseEnCharge.class))).thenReturn(savedPriseEnCharge);

        // Act
        PriseEnCharge result = priseEnChargeService.rejeterPriseEnCharge(1L);

        // Assert
        assertEquals(StatutPriseEnCharge.REJETEE, result.getStatut());
        verify(priseEnChargeRepository).findById(1L);
        verify(priseEnChargeRepository).save(savedPriseEnCharge);
    }

    @Test
    void rejeterPriseEnCharge_WhenNotPending_ShouldThrowException() {
        // Arrange
        savedPriseEnCharge.setStatut(StatutPriseEnCharge.ACCEPTEE);
        when(priseEnChargeRepository.findById(1L)).thenReturn(Optional.of(savedPriseEnCharge));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            priseEnChargeService.rejeterPriseEnCharge(1L);
        });
        verify(priseEnChargeRepository).findById(1L);
        verify(priseEnChargeRepository, never()).save(any());
    }

    @Test
    void accepterPriseEnCharge_WhenPending_ShouldAccept() {
        // Arrange
        savedPriseEnCharge.setStatut(StatutPriseEnCharge.EN_ATTENTE);
        when(priseEnChargeRepository.findById(1L)).thenReturn(Optional.of(savedPriseEnCharge));
        when(priseEnChargeRepository.save(any(PriseEnCharge.class))).thenReturn(savedPriseEnCharge);

        // Act
        PriseEnCharge result = priseEnChargeService.accepterPriseEnCharge(1L);

        // Assert
        assertEquals(StatutPriseEnCharge.ACCEPTEE, result.getStatut());
        verify(priseEnChargeRepository).findById(1L);
        verify(priseEnChargeRepository).save(savedPriseEnCharge);
    }

    @Test
    void accepterPriseEnCharge_WhenNotPending_ShouldThrowException() {
        // Arrange
        savedPriseEnCharge.setStatut(StatutPriseEnCharge.REJETEE);
        when(priseEnChargeRepository.findById(1L)).thenReturn(Optional.of(savedPriseEnCharge));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            priseEnChargeService.accepterPriseEnCharge(1L);
        });
        verify(priseEnChargeRepository).findById(1L);
        verify(priseEnChargeRepository, never()).save(any());
    }
}