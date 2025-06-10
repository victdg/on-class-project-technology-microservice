package com.example.resilient_api.infrastructure.entrypoints.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class TechnologyCreateRequest {
    private String name;
    private String description;
}
