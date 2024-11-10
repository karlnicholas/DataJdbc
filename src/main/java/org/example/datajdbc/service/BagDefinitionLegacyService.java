package org.example.datajdbc.service;

import org.example.datajdbc.domain.BagDefinitionLegacy;
import org.example.datajdbc.repository.BagDefinitionLegacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BagDefinitionLegacyService {

    private final BagDefinitionLegacyRepository bagDefinitionLegacyRepository;

    @Autowired
    public BagDefinitionLegacyService(BagDefinitionLegacyRepository bagDefinitionLegacyRepository) {
        this.bagDefinitionLegacyRepository = bagDefinitionLegacyRepository;
    }

    public List<BagDefinitionLegacy> getBagDefinitionsForOriginAndDateRange(
            String originCc, String originSlic, String originSort,
            LocalDate startDate, LocalDate endDate) {
        return bagDefinitionLegacyRepository.findByOriginCcAndOriginSlicAndOriginSortAndDateRangeOverlap(originCc, originSlic, originSort, startDate, endDate);
    }

    public List<BagDefinitionLegacy> findAll() {
        return bagDefinitionLegacyRepository.findAll();
    }

    public Optional<BagDefinitionLegacy> findById(Long id) {
        return bagDefinitionLegacyRepository.findById(id);
    }

    public BagDefinitionLegacy createBagDefinition(BagDefinitionLegacy bagDefinitionLegacy) {
        return bagDefinitionLegacyRepository.save(bagDefinitionLegacy);
    }

    public Optional<BagDefinitionLegacy> updateBagDefinition(BagDefinitionLegacy bagDefinitionLegacy) {
        Optional<BagDefinitionLegacy> optionalBagDefinitionLegacy = bagDefinitionLegacyRepository.findById(bagDefinitionLegacy.getId());

        if (optionalBagDefinitionLegacy.isPresent()) {
            var existingBagDefinitionLegacy = optionalBagDefinitionLegacy.get();

            // Return the updated BagDefinitionLegacy
            return Optional.of(bagDefinitionLegacyRepository.save(existingBagDefinitionLegacy));
        } else {
            return Optional.empty();
        }
    }

    public void delete(Long id) {
        bagDefinitionLegacyRepository.deleteById(id);
    }
}
