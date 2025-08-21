package com.financialapp.service.impl;

import com.financialapp.dto.RewardCatalogDTO;
import com.financialapp.entity.Partner;
import com.financialapp.entity.RewardCatalog;
import com.financialapp.repository.PartnerRepository;
import com.financialapp.repository.RewardCatalogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RewardCatalogServiceImplTest {

    @Mock
    private RewardCatalogRepository catalogRepository;

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RewardCatalogServiceImpl rewardCatalogService;

    private RewardCatalogDTO rewardCatalogDTO;
    private RewardCatalog rewardCatalog;
    private Partner partner;

    @BeforeEach
    void setUp() {
        // Setup test data
        partner = new Partner();
        partner.setId(1);
        partner.setName("Test Partner");

        rewardCatalog = new RewardCatalog();
        rewardCatalog.setId(1);
        rewardCatalog.setName("Test Reward");
        rewardCatalog.setRewardType("VOUCHER");
        rewardCatalog.setPointsRequired(1000);
        rewardCatalog.setConfiguration("{}");
        rewardCatalog.setActive(true);
        rewardCatalog.setPartner(partner);

        rewardCatalogDTO = new RewardCatalogDTO();
        rewardCatalogDTO.setId(1);
        rewardCatalogDTO.setName("Test Reward");
        rewardCatalogDTO.setRewardType("VOUCHER");
        rewardCatalogDTO.setPointsRequired(1000);
        rewardCatalogDTO.setConfiguration("{}");
        rewardCatalogDTO.setActive(true);
        rewardCatalogDTO.setPartnerId(1);
    }

    @Test
    void createRewardCatalog_Success() {
        // Arrange
        when(partnerRepository.findById(1)).thenReturn(Optional.of(partner));
        when(modelMapper.map(rewardCatalogDTO, RewardCatalog.class)).thenReturn(rewardCatalog);
        when(catalogRepository.save(any(RewardCatalog.class))).thenReturn(rewardCatalog);
        when(modelMapper.map(rewardCatalog, RewardCatalogDTO.class)).thenReturn(rewardCatalogDTO);

        // Act
        RewardCatalogDTO result = rewardCatalogService.createRewardCatalog(rewardCatalogDTO);

        // Assert
        assertNotNull(result);
        assertEquals(rewardCatalogDTO.getId(), result.getId());
        assertEquals(rewardCatalogDTO.getName(), result.getName());
        verify(partnerRepository, times(1)).findById(1);
        verify(catalogRepository, times(1)).save(any(RewardCatalog.class));
    }

    @Test
    void createRewardCatalog_PartnerNotFound() {
        // Arrange
        when(partnerRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rewardCatalogService.createRewardCatalog(rewardCatalogDTO);
        });

        assertEquals("Partner not found", exception.getMessage());
        verify(partnerRepository, times(1)).findById(1);
        verify(catalogRepository, never()).save(any());
    }

    @Test
    void getRewardCatalogById_Success() {
        // Arrange
        when(catalogRepository.findById(1)).thenReturn(Optional.of(rewardCatalog));
        when(modelMapper.map(rewardCatalog, RewardCatalogDTO.class)).thenReturn(rewardCatalogDTO);

        // Act
        RewardCatalogDTO result = rewardCatalogService.getRewardCatalogById(1);

        // Assert
        assertNotNull(result);
        assertEquals(rewardCatalogDTO.getId(), result.getId());
        verify(catalogRepository, times(1)).findById(1);
    }

    @Test
    void getRewardCatalogById_NotFound() {
        // Arrange
        when(catalogRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rewardCatalogService.getRewardCatalogById(1);
        });

        assertEquals("Catalog item not found", exception.getMessage());
        verify(catalogRepository, times(1)).findById(1);
    }

    @Test
    void getAllRewardCatalogs_Success() {
        // Arrange
        RewardCatalog rewardCatalog2 = new RewardCatalog();
        rewardCatalog2.setId(2);
        rewardCatalog2.setName("Test Reward 2");

        List<RewardCatalog> catalogs = Arrays.asList(rewardCatalog, rewardCatalog2);
        
        RewardCatalogDTO dto2 = new RewardCatalogDTO();
        dto2.setId(2);
        dto2.setName("Test Reward 2");

        when(catalogRepository.findAll()).thenReturn(catalogs);
        when(modelMapper.map(rewardCatalog, RewardCatalogDTO.class)).thenReturn(rewardCatalogDTO);
        when(modelMapper.map(rewardCatalog2, RewardCatalogDTO.class)).thenReturn(dto2);

        // Act
        List<RewardCatalogDTO> result = rewardCatalogService.getAllRewardCatalogs();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(catalogRepository, times(1)).findAll();
        verify(modelMapper, times(2)).map(any(RewardCatalog.class), eq(RewardCatalogDTO.class));
    }

    @Test
    void getAllRewardCatalogs_EmptyList() {
        // Arrange
        when(catalogRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<RewardCatalogDTO> result = rewardCatalogService.getAllRewardCatalogs();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(catalogRepository, times(1)).findAll();
    }

    @Test
    void updateRewardCatalog_Success() {
        // Arrange
        RewardCatalogDTO updatedDTO = new RewardCatalogDTO();
        updatedDTO.setName("Updated Reward");
        updatedDTO.setRewardType("DISCOUNT");
        updatedDTO.setPointsRequired(1500);
        updatedDTO.setConfiguration("{\"discount\": 10}");
        updatedDTO.setActive(false);
        updatedDTO.setPartnerId(1);

        when(catalogRepository.findById(1)).thenReturn(Optional.of(rewardCatalog));
        when(partnerRepository.findById(1)).thenReturn(Optional.of(partner));
        when(catalogRepository.save(any(RewardCatalog.class))).thenReturn(rewardCatalog);
        when(modelMapper.map(rewardCatalog, RewardCatalogDTO.class)).thenReturn(updatedDTO);

        // Act
        RewardCatalogDTO result = rewardCatalogService.updateRewardCatalog(1, updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Reward", result.getName());
        assertEquals("DISCOUNT", result.getRewardType());
        assertEquals(1500, result.getPointsRequired());
        assertEquals("{\"discount\": 10}", result.getConfiguration());
        assertFalse(result.isActive());
        
        verify(catalogRepository, times(1)).findById(1);
        verify(partnerRepository, times(1)).findById(1);
        verify(catalogRepository, times(1)).save(rewardCatalog);
    }

    @Test
    void updateRewardCatalog_CatalogNotFound() {
        // Arrange
        when(catalogRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rewardCatalogService.updateRewardCatalog(1, rewardCatalogDTO);
        });

        assertEquals("Catalog item not found", exception.getMessage());
        verify(catalogRepository, times(1)).findById(1);
        verify(catalogRepository, never()).save(any());
    }

    @Test
    void updateRewardCatalog_PartnerNotFound() {
        // Arrange
        when(catalogRepository.findById(1)).thenReturn(Optional.of(rewardCatalog));
        when(partnerRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rewardCatalogService.updateRewardCatalog(1, rewardCatalogDTO);
        });

        assertEquals("Partner not found", exception.getMessage());
        verify(catalogRepository, times(1)).findById(1);
        verify(partnerRepository, times(1)).findById(1);
        verify(catalogRepository, never()).save(any());
    }

    @Test
    void updateRewardCatalog_WithoutPartnerChange() {
        // Arrange
        RewardCatalogDTO updatedDTO = new RewardCatalogDTO();
        updatedDTO.setName("Updated Reward");
        updatedDTO.setRewardType("DISCOUNT");
        updatedDTO.setPointsRequired(1500);
        updatedDTO.setConfiguration("{\"discount\": 10}");
        updatedDTO.setActive(false);
        // No partnerId set - should not try to update partner

        when(catalogRepository.findById(1)).thenReturn(Optional.of(rewardCatalog));
        when(catalogRepository.save(any(RewardCatalog.class))).thenReturn(rewardCatalog);
        when(modelMapper.map(rewardCatalog, RewardCatalogDTO.class)).thenReturn(updatedDTO);

        // Act
        RewardCatalogDTO result = rewardCatalogService.updateRewardCatalog(1, updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Reward", result.getName());
        verify(catalogRepository, times(1)).findById(1);
        verify(partnerRepository, never()).findById(anyInt()); // Should not try to find partner
        verify(catalogRepository, times(1)).save(rewardCatalog);
    }

    @Test
    void deleteRewardCatalog_Success() {
        // Arrange
        when(catalogRepository.existsById(1)).thenReturn(true);
        doNothing().when(catalogRepository).deleteById(1);

        // Act & Assert
        assertDoesNotThrow(() -> {
            rewardCatalogService.deleteRewardCatalog(1);
        });

        verify(catalogRepository, times(1)).existsById(1);
        verify(catalogRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteRewardCatalog_NotFound() {
        // Arrange
        when(catalogRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rewardCatalogService.deleteRewardCatalog(1);
        });

        assertEquals("Catalog item not found with id: 1", exception.getMessage());
        verify(catalogRepository, times(1)).existsById(1);
        verify(catalogRepository, never()).deleteById(1);
    }
}