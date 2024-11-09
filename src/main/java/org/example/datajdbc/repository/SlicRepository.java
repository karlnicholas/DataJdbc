package org.example.datajdbc.repository;

import org.example.datajdbc.domain.Slic;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface SlicRepository extends ListCrudRepository<Slic, Long> {
}
