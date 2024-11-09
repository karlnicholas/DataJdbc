package org.example.datajdbc.repository;

import org.example.datajdbc.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface SortRepository extends CrudRepository<Sort, Long> {
    Optional<Sort> findBySort(String sort);
}
