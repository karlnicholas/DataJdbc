package org.example.datajdbc.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("COUNTRY") // Mapping to the COUNTRY table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"id"})
public class Country {

    @Id
    private Long id;
    private String cc; // 2-character country code
    private String description; // country description
}
