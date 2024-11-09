package org.example.datajdbc.repository;

import org.example.datajdbc.domain.Country;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country, Long> {
    Optional<Country> findByCc(String cc);
}
