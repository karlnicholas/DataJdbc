package org.example.datajdbc.repository;

import org.example.datajdbc.domain.BagDefinitionView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BagDefinitionViewRepository {

    List<BagDefinitionView> findAll();

    Optional<BagDefinitionView> findById(Long id);

    List<BagDefinitionView> findByOriginAndDateRange(
            String originCc, String originSlic, String originSort,
            LocalDate startDate, LocalDate endDate);
}
