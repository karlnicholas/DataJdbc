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

    public BagDefinitionView createBagDefinition(BagDefinitionView bagDefinitionView) {
        FlowNode origin = flowNodeService.getOrCreateFlowNode(bagDefinitionView.getOriginCc(),
                bagDefinitionView.getOriginSlic(),
                bagDefinitionView.getOriginSort());
        FlowNode destination = flowNodeService.getOrCreateFlowNode(bagDefinitionView.getDestinationCc(),
                bagDefinitionView.getDestinationSlic(),
                bagDefinitionView.getDestinationSort());

        var bagDefinition = new BagDefinition(null, origin.getId(), destination.getId(), bagDefinitionView.getStartDate(), bagDefinitionView.getEndDate());

        bagDefinitionRepository.save(bagDefinition);
        // Find and return the newly created BagDefinitionView
        return bagDefinitionViewRepository.findById(bagDefinition.getId())
                .orElseThrow(() -> new IllegalStateException("BagDefinitionView could not be created"));
    }

    public Optional<BagDefinitionView> updateBagDefinition(BagDefinitionView bagDefinitionView) {
        Optional<BagDefinition> optionalBagDefinition = bagDefinitionRepository.findById(bagDefinitionView.getBagDefinitionId());

        if (optionalBagDefinition.isPresent()) {
            var existingBagDefinition = optionalBagDefinition.get();

            FlowNode origin = flowNodeService.getOrCreateFlowNode(bagDefinitionView.getOriginCc(),
                    bagDefinitionView.getOriginSlic(),
                    bagDefinitionView.getOriginSort());
            FlowNode destination = flowNodeService.getOrCreateFlowNode(bagDefinitionView.getDestinationCc(),
                    bagDefinitionView.getDestinationSlic(),
                    bagDefinitionView.getDestinationSort());


            existingBagDefinition.setOriginId(origin.getId());
            existingBagDefinition.setDestinationId(destination.getId());
            existingBagDefinition.setStartDate(bagDefinitionView.getStartDate());
            existingBagDefinition.setEndDate(bagDefinitionView.getEndDate());

            bagDefinitionRepository.save(existingBagDefinition);
            // Return the updated BagDefinitionView
            return bagDefinitionViewRepository.findById(bagDefinitionView.getBagDefinitionId());
        } else {
            return Optional.empty();
        }
    }

    public void delete(Long id) {
        bagDefinitionRepository.deleteById(id);
    }
}
