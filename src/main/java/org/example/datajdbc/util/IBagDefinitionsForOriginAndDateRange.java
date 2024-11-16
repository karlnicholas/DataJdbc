package org.example.datajdbc.util;

import org.example.datajdbc.domain.BagDefinitionView;

import java.time.LocalDate;
import java.util.List;

public interface IBagDefinitionsForOriginAndDateRange {
    List<BagDefinitionView> getBagDefinitionsForOriginAndDateRange(
            String originCc, String originSlic, String originSort,
            LocalDate startDate, LocalDate endDate);
}
