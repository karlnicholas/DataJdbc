package org.example.datajdbc.service;

import lombok.extern.slf4j.Slf4j;
import org.example.datajdbc.domain.BagDefinition;
import org.example.datajdbc.domain.BagDefinitionView;
import org.example.datajdbc.domain.FlowNode;
import org.example.datajdbc.repository.BagDefinitionRepository;
import org.example.datajdbc.repository.BagDefinitionViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ApiService {

    private final BagDefinitionRepository bagDefinitionRepository;
    private final FlowNodeService flowNodeService;
    private final BagDefinitionViewRepository bagDefinitionViewRepository;

    @Autowired
    public ApiService(BagDefinitionRepository bagDefinitionRepository, FlowNodeService flowNodeService, BagDefinitionViewRepository bagDefinitionViewRepository) {
        this.bagDefinitionRepository = bagDefinitionRepository;
        this.flowNodeService = flowNodeService;
        this.bagDefinitionViewRepository = bagDefinitionViewRepository;
    }

    public BagDefinition createBagDefinition(
            String originCc, String originSlic, String originSort,
            String destinationCc, String destinationSlic, String destinationSort,
            LocalDate startDate, LocalDate endDate) {

        // Get or create origin and destination FlowNodes
        FlowNode origin = flowNodeService.getOrCreateFlowNode(originCc, originSlic, originSort);
        FlowNode destination = flowNodeService.getOrCreateFlowNode(destinationCc, destinationSlic, destinationSort);

        // Create a new BagDefinition instance with IDs instead of objects
        BagDefinition bagDefinition = BagDefinition.builder()
                .originId(origin.getId())
                .destinationId(destination.getId())
                .startDate(startDate)
                .endDate(endDate)
                .build();

        return bagDefinitionRepository.save(bagDefinition);
    }

    /**
     * Retrieves BagDefinitionView entries filtered by originCc, originSlic, originSort, and a date range.
     *
     * @param originCc   The origin country code.
     * @param originSlic The origin SLIC code.
     * @param originSort The origin sort code.
     * @param startDate  The start date for filtering.
     * @param endDate    The end date for filtering.
     * @return A list of BagDefinitionView entries matching the criteria.
     */
    public List<BagDefinitionView> getBagDefinitionsForOriginAndDateRange(
            String originCc, String originSlic, String originSort,
            LocalDate startDate, LocalDate endDate) {

        // Use BagDefinitionViewRepository instead of JdbcTemplate for querying
        return bagDefinitionViewRepository.findByOriginAndDateRange(originCc, originSlic, originSort, startDate, endDate);
    }
}
