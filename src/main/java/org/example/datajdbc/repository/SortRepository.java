package org.example.datajdbc.repository;

import org.example.datajdbc.domain.Sort;
import org.springframework.data.repository.ListCrudRepository;

public interface SortRepository extends ListCrudRepository<Sort, Long> {
    // CRUD operations for Sort entity using code
}
