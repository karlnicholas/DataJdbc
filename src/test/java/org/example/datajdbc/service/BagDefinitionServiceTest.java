package org.example.datajdbc.service;

import org.example.datajdbc.domain.BagDefinition;
import org.example.datajdbc.domain.BagDefinitionView;
import org.example.datajdbc.domain.FlowNode;
import org.example.datajdbc.repository.BagDefinitionRepository;
import org.example.datajdbc.repository.BagDefinitionViewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BagDefinitionServiceTest {

    @Mock
    private BagDefinitionViewRepository bagDefinitionViewRepository;

    @Mock
    private BagDefinitionRepository bagDefinitionRepository;

    @Mock
    private FlowNodeService flowNodeService;

    @InjectMocks
    private BagDefinitionService bagDefinitionService;

    @Test
    void testGetBagDefinitionsForOriginAndDateRange() {
        // Arrange
        String originCc = "US";
        String originSlic = "0871";
        String originSort = "L";
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        BagDefinitionView bagDefinitionView = new BagDefinitionView(
                1L, startDate, endDate,
                originCc, originSlic, originSort,
                "US", "9449", "L"
        );

        when(bagDefinitionViewRepository.findByOriginAndDateRange(
                originCc, originSlic, originSort, startDate, endDate))
                .thenReturn(Collections.singletonList(bagDefinitionView));

        // Act
        List<BagDefinitionView> result = bagDefinitionService.getBagDefinitionsForOriginAndDateRange(
                originCc, originSlic, originSort, startDate, endDate
        );

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(bagDefinitionView);
    }

    @Test
    void testCreateBagDefinition() {
        // Arrange
        String originCc = "US";
        String originSlic = "0871";
        String originSort = "L";
        String destinationCc = "US";
        String destinationSlic = "9449";
        String destinationSort = "L";
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        FlowNode originFlowNode = new FlowNode(1L, null, null, null);
        FlowNode destinationFlowNode = new FlowNode(2L, null, null, null);

        // Define expected BagDefinitionView
        BagDefinitionView bagDefinitionView = new BagDefinitionView(
                1L, startDate, endDate,
                originCc, originSlic, originSort,
                destinationCc, destinationSlic, destinationSort
        );

        // Stubbing mocks for FlowNodeService
        when(flowNodeService.getOrCreateFlowNode(originCc, originSlic, originSort)).thenReturn(originFlowNode);
        when(flowNodeService.getOrCreateFlowNode(destinationCc, destinationSlic, destinationSort)).thenReturn(destinationFlowNode);
        when(bagDefinitionViewRepository.findById(1L)).thenReturn(Optional.of(bagDefinitionView));

        // Capture the BagDefinition passed to save() and set an ID manually
        ArgumentCaptor<BagDefinition> bagDefinitionCaptor = ArgumentCaptor.forClass(BagDefinition.class);
        when(bagDefinitionRepository.save(bagDefinitionCaptor.capture())).thenAnswer(invocation -> {
            BagDefinition savedBagDefinition = bagDefinitionCaptor.getValue();
            savedBagDefinition.setId(1L);  // Simulate ID assignment
            return savedBagDefinition;
        });

        // Act
        BagDefinitionView result = bagDefinitionService.createBagDefinition(
                BagDefinitionView.builder()
                        .originCc(originCc)
                        .originSlic(originSlic)
                        .originSort(originSort)
                        .destinationCc(destinationCc)
                        .destinationSlic(destinationSlic)
                        .destinationSort(destinationSort)
                        .startDate(startDate)
                        .endDate(endDate)
                        .build()
        );

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(bagDefinitionView);

        // Verify interactions
        verify(flowNodeService, times(1)).getOrCreateFlowNode(originCc, originSlic, originSort);
        verify(flowNodeService, times(1)).getOrCreateFlowNode(destinationCc, destinationSlic, destinationSort);
        verify(bagDefinitionRepository, times(1)).save(bagDefinitionCaptor.getValue());
        verify(bagDefinitionViewRepository, times(1)).findById(1L);
    }
}
