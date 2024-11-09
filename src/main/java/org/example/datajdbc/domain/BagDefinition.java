package org.example.datajdbc.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table(name = "BAGDEFINITION")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"id"})
public class BagDefinition {
    @Id
    private Long id;
    private Long originId;       // Reference to FlowNode ID for origin
    private Long destinationId;  // Reference to FlowNode ID for destination
    private LocalDate startDate;
    private LocalDate endDate;
}
