package org.example.datajdbc.repository;

import org.example.datajdbc.domain.BagDefinitionView;
import org.springframework.data.repository.Repository;

import java.time.LocalDate;
import java.util.List;

public interface BagDefinitionViewRepository extends Repository<BagDefinitionView, Long> {
    List<BagDefinitionView> findByOriginAndDateRange(String originCc, String originSlic, String originSort, LocalDate startDate, LocalDate endDate);
}
