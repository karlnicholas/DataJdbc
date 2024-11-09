package org.example.datajdbc.service;

import org.example.datajdbc.domain.BagDefinition;
import org.example.datajdbc.domain.BagDefinitionView;
import org.example.datajdbc.domain.FlowNode;
import org.example.datajdbc.repository.BagDefinitionRepository;
import org.example.datajdbc.repository.BagDefinitionViewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiServiceTest {

    @Mock
    private BagDefinitionViewRepository bagDefinitionViewRepository;

    @Mock
    private BagDefinitionRepository bagDefinitionRepository;

    @Mock
    private FlowNodeService flowNodeService;

    @InjectMocks
    private ApiService apiService;

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
        List<BagDefinitionView> result = apiService.getBagDefinitionsForOriginAndDateRange(
                originCc, originSlic, originSort, startDate, endDate
        );

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getOriginCc()).isEqualTo(originCc);
        assertThat(result.get(0).getOriginSlic()).isEqualTo(originSlic);
        assertThat(result.get(0).getOriginSort()).isEqualTo(originSort);
        assertThat(result.get(0).getDestinationCc()).isEqualTo("US");
        assertThat(result.get(0).getDestinationSlic()).isEqualTo("9449");
        assertThat(result.get(0).getDestinationSort()).isEqualTo("L");
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

        BagDefinition bagDefinition = BagDefinition.builder()
                .originId(originFlowNode.getId())
                .destinationId(destinationFlowNode.getId())
                .startDate(startDate)
                .endDate(endDate)
                .build();

        when(flowNodeService.getOrCreateFlowNode(originCc, originSlic, originSort)).thenReturn(originFlowNode);
        when(flowNodeService.getOrCreateFlowNode(destinationCc, destinationSlic, destinationSort)).thenReturn(destinationFlowNode);
        when(bagDefinitionRepository.save(any(BagDefinition.class))).thenReturn(bagDefinition);

        // Act
        BagDefinition result = apiService.createBagDefinition(
                originCc, originSlic, originSort,
                destinationCc, destinationSlic, destinationSort,
                startDate, endDate
        );

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getOriginId()).isEqualTo(originFlowNode.getId());
        assertThat(result.getDestinationId()).isEqualTo(destinationFlowNode.getId());
        assertThat(result.getStartDate()).isEqualTo(startDate);
        assertThat(result.getEndDate()).isEqualTo(endDate);

        verify(flowNodeService, times(1)).getOrCreateFlowNode(originCc, originSlic, originSort);
        verify(flowNodeService, times(1)).getOrCreateFlowNode(destinationCc, destinationSlic, destinationSort);
        verify(bagDefinitionRepository, times(1)).save(any(BagDefinition.class));
    }
}
