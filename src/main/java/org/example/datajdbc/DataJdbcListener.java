package org.example.datajdbc;

import org.example.datajdbc.domain.*;
import org.example.datajdbc.repository.FlowNodeRepository;
import org.example.datajdbc.repository.SlicRepository;
import org.example.datajdbc.repository.SortRepository;
import org.example.datajdbc.repository.CountryRepository;
import org.example.datajdbc.repository.BagDefinitionRepository;
import org.example.datajdbc.service.ApiService;
import org.example.datajdbc.service.FlowNodeService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataJdbcListener {

    private final SortRepository sortRepository;
    private final CountryRepository countryRepository;
    private final SlicRepository slicRepository;
    private final FlowNodeRepository flowNodeRepository;
    private final BagDefinitionRepository bagDefinitionRepository;
    private final ApiService apiService;
    private final FlowNodeService flowNodeService;

    public DataJdbcListener(SortRepository sortRepository, CountryRepository countryRepository, SlicRepository slicRepository, FlowNodeRepository flowNodeRepository, BagDefinitionRepository bagDefinitionRepository, ApiService apiService, FlowNodeService flowNodeService) {
        this.sortRepository = sortRepository;
        this.countryRepository = countryRepository;
        this.slicRepository = slicRepository;
        this.flowNodeRepository = flowNodeRepository;
        this.bagDefinitionRepository = bagDefinitionRepository;
        this.apiService = apiService;
        this.flowNodeService = flowNodeService;
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        flowNodeService.initialize();
        // Display initial data for Sort entities
        List<Sort> allSorts = sortRepository.findAll();
        System.out.println("Initial Sort data:");
        allSorts.forEach(System.out::println);

        // Display initial data for Country entities
        List<Country> allCountries = countryRepository.findAll();
        System.out.println("Initial Country data:");
        allCountries.forEach(System.out::println);

        // Display initial data for Slic entities
        List<Slic> allSlics = slicRepository.findAll();
        System.out.println("Initial Slic data:");
        allSlics.forEach(System.out::println);

        // Display initial data for FlowNode entities
        List<FlowNode> allFlowNodes = flowNodeRepository.findAll();
        System.out.println("Initial FlowNode data:");
        allFlowNodes.forEach(System.out::println);

//        // Use ApiService to create a new BagDefinition and retrieve it
//        BagDefinition newBagDefinition = apiService.createBagDefinition("US", "0871", "L", "US", "9449", "L", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));

        // Print the newly created BagDefinition
        List<BagDefinitionView> views = apiService.getBagDefinitionsForOriginAndDateRange("US", "0871", "L", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
        views.forEach(System.out::println);
    }
}
