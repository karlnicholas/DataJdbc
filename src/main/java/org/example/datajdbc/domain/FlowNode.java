package org.example.datajdbc.domain;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="FLOWNODE")
@ToString(exclude = {"id"})
public class FlowNode {
    private Long id; // Primary key for FlowNode
    @Column("countryId")
    private Long countryId; // Reference to Country primary key
    @Column("slicId")
    private Long slicId;    // Reference to Slic primary key
    @Column("sortId")
    private Long sortId;    // Reference to Sort primary key
}
