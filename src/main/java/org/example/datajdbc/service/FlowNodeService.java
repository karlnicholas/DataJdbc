package org.example.datajdbc.service;

import org.example.datajdbc.domain.FlowNode;
import org.example.datajdbc.repository.CountryRepository;
import org.example.datajdbc.repository.FlowNodeRepository;
import org.example.datajdbc.repository.SlicRepository;
import org.example.datajdbc.repository.SortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FlowNodeService {

    private final FlowNodeRepository flowNodeRepository;
    private final CountryRepository countryRepository;
    private final SlicRepository slicRepository;
    private final SortRepository sortRepository;

    // Cache keyed by "country:slic:sort" (String keys for easy lookup)
    private final Map<String, FlowNode> flowNodeCache = new ConcurrentHashMap<>();

    @Autowired
    public FlowNodeService(FlowNodeRepository flowNodeRepository, CountryRepository countryRepository, SlicRepository slicRepository, SortRepository sortRepository) {
        this.flowNodeRepository = flowNodeRepository;
        this.countryRepository = countryRepository;
        this.slicRepository = slicRepository;
        this.sortRepository = sortRepository;
    }

    // Load all FlowNodes into the cache on startup
    public void initialize() {
        flowNodeRepository.findAll().forEach(this::addToCache);
    }

    // Adds a FlowNode to the cache with a "country:slic:sort" key
    private void addToCache(FlowNode flowNode) {
        String key = generateCacheKey(flowNode.getCountryId(), flowNode.getSlicId(), flowNode.getSortId());
        flowNodeCache.put(key, flowNode);
    }

    // Main method for retrieving or creating a FlowNode by "country", "slic", "sort" (String) values
    public FlowNode getOrCreateFlowNode(String country, String slic, String sort) {
        String key = generateCacheKey(country, slic, sort);

        // Check cache first
        FlowNode flowNode = flowNodeCache.get(key);
        if (flowNode != null) {
            return flowNode;
        }

        // If not found in cache, lookup IDs for Country, Slic, and Sort, then create and cache a new FlowNode
        Long countryId = countryRepository.findByCode(country).orElseThrow(() -> new IllegalArgumentException("Country not found")).getId();
        Long slicId = slicRepository.findByCode(slic).orElseThrow(() -> new IllegalArgumentException("Slic not found")).getId();
        Long sortId = sortRepository.findByCode(sort).orElseThrow(() -> new IllegalArgumentException("Sort not found")).getId();

        flowNode = new FlowNode(null, countryId, slicId, sortId);
        flowNode = flowNodeRepository.save(flowNode);

        // Add the newly created FlowNode to the cache
        flowNodeCache.put(key, flowNode);

        return flowNode;
    }

    // Generates a cache key from "country", "slic", and "sort" String values
    private String generateCacheKey(String country, String slic, String sort) {
        return country + ":" + slic + ":" + sort;
    }

    // Overloaded method to generate cache keys directly from IDs (for initial cache loading)
    private String generateCacheKey(Long countryId, Long slicId, Long sortId) {
        return countryId + ":" + slicId + ":" + sortId;
    }
}
