package com.example.resilient_api.domain.api;

import com.example.resilient_api.domain.model.Technology;
import reactor.core.publisher.Mono;

public interface TechnologyServicePort {
    Mono<Long> registerTechnology(Technology technology);
}
