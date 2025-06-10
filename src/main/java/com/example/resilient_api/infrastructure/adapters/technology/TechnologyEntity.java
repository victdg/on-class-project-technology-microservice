package com.example.resilient_api.infrastructure.adapters.technology;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "technologies")
@Getter
@Setter
@RequiredArgsConstructor
public class TechnologyEntity {
    @Id
    private Long id;
    private String name;
    private String description;
}
