package org.example.datajdbc.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
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
    @Column("originCc")
    private String originCc;
    @Column("originSlic")
    private String originSlic;
    @Column("originSort")
    private String originSort;
    @Column("destinationCc")
    private String destinationCc;
    @Column("destinationSlic")
    private String destinationSlic;
    @Column("destinationSort")
    private String destinationSort;
    @Column("startDate")
    private LocalDate startDate;
    @Column("endDate")
    private LocalDate endDate;
}
