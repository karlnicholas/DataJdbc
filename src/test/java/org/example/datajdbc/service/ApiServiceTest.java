package org.example.datajdbc.service;

import org.example.datajdbc.domain.BagDefinitionView;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiServiceTest {

    @Mock
    private BagDefinitionViewRepository bagDefinitionViewRepository;

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
    }
}
