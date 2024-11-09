package org.example.datajdbc.repository;

import org.example.datajdbc.domain.Country;
import org.springframework.data.repository.ListCrudRepository;

public interface CountryRepository extends ListCrudRepository<Country, Long> {
    // Additional query methods can be defined here if needed
}
