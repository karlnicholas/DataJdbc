package org.example.datajdbc.service;

import org.example.datajdbc.domain.BagDefinitionView;
import org.example.datajdbc.repository.BagDefinitionRepository;
import org.example.datajdbc.repository.BagDefinitionViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public List<BagDefinitionView> getBagDefinitionsForOriginAndDateRange(String originCc, String originSlic, String originSort, LocalDate startDate, LocalDate endDate) {
        return bagDefinitionViewRepository.findByOriginAndDateRange(originCc, originSlic, originSort, startDate, endDate);
    }

    // Other ApiService methods...
}
