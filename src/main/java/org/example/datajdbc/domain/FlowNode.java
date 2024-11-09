package org.example.datajdbc.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlowNode {
    private Long id; // Primary key for FlowNode
    private Long countryId; // Reference to Country primary key
    private Long slicId;    // Reference to Slic primary key
    private Long sortId;    // Reference to Sort primary key
}
