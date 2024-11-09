package org.example.datajdbc.repository;

import org.example.datajdbc.domain.BagDefinitionView;

import java.time.LocalDate;
import java.util.List;

public interface BagDefinitionViewRepository {
    List<BagDefinitionView> findByOriginAndDateRange(String originCc, String originSlic, String originSort, LocalDate startDate, LocalDate endDate);
}
