package org.example.datajdbc.service;

import org.example.datajdbc.domain.BagDefinitionLegacy;
import org.example.datajdbc.domain.BagDefinitionView;
import org.example.datajdbc.repository.BagDefinitionLegacyRepository;
import org.example.datajdbc.util.IBagDefinitionsForOriginAndDateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BagDefinitionLegacyService implements IBagDefinitionsForOriginAndDateRange {

    private final BagDefinitionLegacyRepository bagDefinitionLegacyRepository;

    @Autowired
    public BagDefinitionLegacyService(BagDefinitionLegacyRepository bagDefinitionLegacyRepository) {
        this.bagDefinitionLegacyRepository = bagDefinitionLegacyRepository;
    }

    @Override
    public List<BagDefinitionView> getBagDefinitionsForOriginAndDateRange(
            String originCc, String originSlic, String originSort,
            LocalDate startDate, LocalDate endDate) {
        return bagDefinitionLegacyRepository.findByOriginCcAndOriginSlicAndOriginSortAndDateRangeOverlap(originCc, originSlic, originSort, startDate, endDate)
                .stream().map(bdl->BagDefinitionView.builder()
                        .bagDefinitionId(bdl.getId())
                        .originCc(bdl.getOriginCc())
                        .originSlic(bdl.getOriginSlic())
                        .originSort(bdl.getOriginSlic())
                        .destinationCc(bdl.getDestinationCc())
                        .destinationSlic(bdl.getDestinationSlic())
                        .destinationSort(bdl.getDestinationSort())
                        .build()).toList();
    }

    public List<BagDefinitionLegacy> findAll() {
        return bagDefinitionLegacyRepository.findAll();
    }

    public Optional<BagDefinitionLegacy> findById(Long id) {
        return bagDefinitionLegacyRepository.findById(id);
    }

    public Long createBagDefinition(BagDefinitionLegacy bagDefinitionLegacy) {
        return bagDefinitionLegacyRepository.save(bagDefinitionLegacy).getId();
    }

    public Optional<Long> updateBagDefinition(BagDefinitionLegacy bagDefinitionLegacy) {
        Optional<BagDefinitionLegacy> optionalBagDefinitionLegacy = bagDefinitionLegacyRepository.findById(bagDefinitionLegacy.getId());

        if (optionalBagDefinitionLegacy.isPresent()) {
            var existingBagDefinitionLegacy = optionalBagDefinitionLegacy.get();

            // Return the updated BagDefinitionLegacy
            return Optional.of(bagDefinitionLegacyRepository.save(existingBagDefinitionLegacy).getId());
        } else {
            return Optional.empty();
        }
    }

    public void delete(Long id) {
        bagDefinitionLegacyRepository.deleteById(id);
    }
}
