package org.example.datajdbc.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("SORT")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"id"})
public class Sort {
    @Id
    private Long id;

    private String sort;    // char 1
    private String description;

 }
