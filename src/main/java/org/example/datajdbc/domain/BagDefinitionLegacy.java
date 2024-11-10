package org.example.datajdbc.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table(name = "BAGDEFINITIONLEGACY")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"id"})
public class BagDefinitionLegacy {
    @Id
    private Long id;
    private String originCc;
    private String originSlic;
    private String originSort;
    private String destinationCc;
    private String destinationSlic;
    private String destinationSort;
    private LocalDate startDate;
    private LocalDate endDate;
}
