package org.example.datajdbc.repository;

import org.example.datajdbc.domain.FlowNode;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FlowNodeRepository extends ListCrudRepository<FlowNode, Long> {
    Optional<FlowNode> findByCcAndSlicAndSort(String cc, String slic, String sort);
}
