package org.example.datajdbc.repository;

import org.example.datajdbc.domain.Slic;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface SlicRepository extends CrudRepository<Slic, Long> {
    Optional<Slic> findByCode(String code);
}
