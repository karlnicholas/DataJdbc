package org.example.datajdbc.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("FLOWNODE")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"id"})
public class FlowNode {
    @Id
    private Long id;
    private String cc; // char(2)
    private String slic; // char(5)
    private String sort;  // char(1)

}
