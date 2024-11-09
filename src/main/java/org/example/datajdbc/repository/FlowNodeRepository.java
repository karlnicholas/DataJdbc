package org.example.datajdbc.repository;

import org.example.datajdbc.domain.FlowNode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowNodeRepository extends CrudRepository<FlowNode, Long> {
    // Standard CRUD operations only
}
