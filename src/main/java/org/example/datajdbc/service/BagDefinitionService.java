package org.example.datajdbc.service;

import org.example.datajdbc.domain.BagDefinition;
import org.example.datajdbc.domain.BagDefinitionView;
import org.example.datajdbc.domain.FlowNode;
import org.example.datajdbc.repository.BagDefinitionRepository;
import org.example.datajdbc.repository.BagDefinitionViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BagDefinitionService {

    private final BagDefinitionRepository bagDefinitionRepository;
    private final FlowNodeService flowNodeService;
    private final BagDefinitionViewRepository bagDefinitionViewRepository;

    @Autowired
    public BagDefinitionService(BagDefinitionRepository bagDefinitionRepository,
                                FlowNodeService flowNodeService,
                                BagDefinitionViewRepository bagDefinitionViewRepository) {
        this.bagDefinitionRepository = bagDefinitionRepository;
        this.flowNodeService = flowNodeService;
        this.bagDefinitionViewRepository = bagDefinitionViewRepository;
    }

    public List<BagDefinitionView> getBagDefinitionsForOriginAndDateRange(
            String originCc, String originSlic, String originSort,
            LocalDate startDate, LocalDate endDate) {
        return bagDefinitionViewRepository.findByOriginAndDateRange(originCc, originSlic, originSort, startDate, endDate);
    }

    public List<BagDefinitionView> findAll() {
        return bagDefinitionViewRepository.findAll();
    }

    public Optional<BagDefinitionView> findById(Long id) {
        return bagDefinitionViewRepository.findById(id);
    }

    public BagDefinitionView createBagDefinition(String originCc, String originSlic, String originSort,
                                                 String destinationCc, String destinationSlic, String destinationSort,
                                                 LocalDate startDate, LocalDate endDate) {
        FlowNode origin = flowNodeService.getOrCreateFlowNode(originCc, originSlic, originSort);
        FlowNode destination = flowNodeService.getOrCreateFlowNode(destinationCc, destinationSlic, destinationSort);

        var bagDefinition = BagDefinition.builder()
                .originId(origin.getId())
                .destinationId(destination.getId())
                .startDate(startDate)
                .endDate(endDate)
                .build();

        bagDefinitionRepository.save(bagDefinition);
        // Find and return the newly created BagDefinitionView
        return bagDefinitionViewRepository.findById(bagDefinition.getId())
                .orElseThrow(() -> new IllegalStateException("BagDefinitionView could not be created"));
    }

    public Optional<BagDefinitionView> updateBagDefinition(Long id, String originCc, String originSlic, String originSort,
                                                           String destinationCc, String destinationSlic, String destinationSort,
                                                           LocalDate startDate, LocalDate endDate) {
        Optional<BagDefinition> optionalBagDefinition = bagDefinitionRepository.findById(id);

        if (optionalBagDefinition.isPresent()) {
            var existingBagDefinition = optionalBagDefinition.get();

            FlowNode origin = flowNodeService.getOrCreateFlowNode(originCc, originSlic, originSort);
            FlowNode destination = flowNodeService.getOrCreateFlowNode(destinationCc, destinationSlic, destinationSort);

            existingBagDefinition.setOriginId(origin.getId());
            existingBagDefinition.setDestinationId(destination.getId());
            existingBagDefinition.setStartDate(startDate);
            existingBagDefinition.setEndDate(endDate);

            bagDefinitionRepository.save(existingBagDefinition);
            // Return the updated BagDefinitionView
            return bagDefinitionViewRepository.findById(id);
        } else {
            return Optional.empty();
        }
    }

    public void delete(Long id) {
        bagDefinitionRepository.deleteById(id);
    }
}
