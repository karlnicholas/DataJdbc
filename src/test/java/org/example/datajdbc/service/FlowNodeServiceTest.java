package org.example.datajdbc.service;

import org.example.datajdbc.domain.FlowNode;
import org.example.datajdbc.domain.Country;
import org.example.datajdbc.domain.Slic;
import org.example.datajdbc.domain.Sort;
import org.example.datajdbc.repository.CountryRepository;
import org.example.datajdbc.repository.FlowNodeRepository;
import org.example.datajdbc.repository.SlicRepository;
import org.example.datajdbc.repository.SortRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlowNodeServiceTest {

    @Mock
    private FlowNodeRepository flowNodeRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private SlicRepository slicRepository;

    @Mock
    private SortRepository sortRepository;

    @InjectMocks
    private FlowNodeService flowNodeService;

    @Test
    void testInitializeCache() {
        // Arrange
        Long countryId = 100L;
        Long slicId = 200L;
        Long sortId = 300L;
        Country countryEntity = new Country(countryId, "US", "United States");
        Slic slicEntity = new Slic(slicId, "0871", "Origin SLIC");
        Sort sortEntity = new Sort(sortId, "L", "Local");        FlowNode flowNode = new FlowNode(1L, 100L, 200L, 300L);
        when(flowNodeRepository.findAll()).thenReturn(Collections.singletonList(flowNode));
        when(countryRepository.findById(countryId)).thenReturn(Optional.of(countryEntity));
        when(slicRepository.findById(slicId)).thenReturn(Optional.of(slicEntity));
        when(sortRepository.findById(sortId)).thenReturn(Optional.of(sortEntity));
        // Act
        flowNodeService.initialize();

        // Assert
        String expectedKey = flowNodeService.generateCacheKey(countryEntity.getCc(), slicEntity.getSlic(), sortEntity.getSort());
        assertThat(flowNodeService.getFlowNodeCache().containsKey(expectedKey)).isTrue();
        assertThat(flowNodeService.getFlowNodeCache().get(expectedKey)).isEqualTo(flowNode);
        verify(flowNodeRepository, times(1)).findAll();
    }

    @Test
    void testGetOrCreateFlowNode_ReturnsFromCache() {
        // Arrange
        String country = "US";
        String slic = "0871";
        String sort = "L";
        Long countryId = 100L;
        Long slicId = 200L;
        Long sortId = 300L;
        FlowNode flowNode = new FlowNode(1L, countryId, slicId, sortId);

        // Prepopulate the cache with the expected FlowNode
        String key = flowNodeService.generateCacheKey(country, slic, sort);
        flowNodeService.getFlowNodeCache().put(key, flowNode);

        // Act
        FlowNode result = flowNodeService.getOrCreateFlowNode(country, slic, sort);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(flowNode);  // Ensure the cached instance is returned
        verifyNoInteractions(countryRepository, slicRepository, sortRepository, flowNodeRepository);
    }

    @Test
    void testGetOrCreateFlowNode_CreatesNewFlowNode() {
        String country = "US";
        String slic = "0871";
        String sort = "L";

        Long countryId = 100L;
        Long slicId = 200L;
        Long sortId = 300L;

        Country countryEntity = new Country(countryId, "US", "United States");
        Slic slicEntity = new Slic(slicId, "0871", "Origin SLIC");
        Sort sortEntity = new Sort(sortId, "L", "Local");

        FlowNode newFlowNode = new FlowNode(null, countryId, slicId, sortId);

        when(countryRepository.findByCc(country)).thenReturn(Optional.of(countryEntity));
        when(slicRepository.findBySlic(slic)).thenReturn(Optional.of(slicEntity));
        when(sortRepository.findBySort(sort)).thenReturn(Optional.of(sortEntity));
        when(flowNodeRepository.save(any(FlowNode.class))).thenReturn(newFlowNode);

        FlowNode result = flowNodeService.getOrCreateFlowNode(country, slic, sort);

        assertThat(result).isNotNull();
        assertThat(result.getCountryId()).isEqualTo(countryId);
        assertThat(result.getSlicId()).isEqualTo(slicId);
        assertThat(result.getSortId()).isEqualTo(sortId);

        verify(flowNodeRepository, times(1)).save(any(FlowNode.class));
    }

    @Test
    void testGetOrCreateFlowNode_ThrowsExceptionWhenCountryNotFound() {
        String country = "XX";
        String slic = "0871";
        String sort = "L";

        when(countryRepository.findByCc(country)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                flowNodeService.getOrCreateFlowNode(country, slic, sort)
        );
        assertThat(exception.getMessage()).isEqualTo("Country not found");
        verify(countryRepository, times(1)).findByCc(country);
    }

    @Test
    void testGetOrCreateFlowNode_ThrowsExceptionWhenSlicNotFound() {
        String country = "US";
        String slic = "XXXX";
        String sort = "L";

        when(countryRepository.findByCc(country)).thenReturn(Optional.of(new Country(100L, "US", "United States")));
        when(slicRepository.findBySlic(slic)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                flowNodeService.getOrCreateFlowNode(country, slic, sort)
        );
        assertThat(exception.getMessage()).isEqualTo("Slic not found");
        verify(slicRepository, times(1)).findBySlic(slic);
    }

    @Test
    void testGetOrCreateFlowNode_ThrowsExceptionWhenSortNotFound() {
        String country = "US";
        String slic = "0871";
        String sort = "X";

        when(countryRepository.findByCc(country)).thenReturn(Optional.of(new Country(100L, "US", "United States")));
        when(slicRepository.findBySlic(slic)).thenReturn(Optional.of(new Slic(200L, "0871", "Origin SLIC")));
        when(sortRepository.findBySort(sort)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                flowNodeService.getOrCreateFlowNode(country, slic, sort)
        );
        assertThat(exception.getMessage()).isEqualTo("Sort not found");
        verify(sortRepository, times(1)).findBySort(sort);
    }
}
