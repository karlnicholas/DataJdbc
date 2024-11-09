package org.example.datajdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BagDefinitionView {
    private Long bagDefinitionId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String originCc;
    private String originSlic;
    private String originSort;
    private String destinationCc;
    private String destinationSlic;
    private String destinationSort;
}
