package org.example.datajdbc.repository;

import org.example.datajdbc.domain.BagDefinition;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BagDefinitionRepository extends ListCrudRepository<BagDefinition, Long> {
    // Additional custom query methods can be defined here if needed
}
