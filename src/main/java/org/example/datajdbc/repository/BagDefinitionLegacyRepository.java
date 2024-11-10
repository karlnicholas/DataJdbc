package org.example.datajdbc.repository;

import org.example.datajdbc.domain.BagDefinitionLegacy;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface BagDefinitionLegacyRepository extends ListCrudRepository<BagDefinitionLegacy, Long> {

    @Query("SELECT * FROM BAGDEFINITIONLEGACY " +
            "WHERE originCc = :originCc " +
            "AND originSlic = :originSlic " +
            "AND originSort = :originSort " +
            "AND (startDate <= :endDate AND endDate >= :startDate)")
    List<BagDefinitionLegacy> findByOriginCcAndOriginSlicAndOriginSortAndDateRangeOverlap(
            String originCc, String originSlic, String originSort, LocalDate startDate, LocalDate endDate);
}
