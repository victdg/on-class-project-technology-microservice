package com.example.resilient_api.infrastructure.adapters.technology;

import com.example.resilient_api.domain.enums.TechnicalMessage;
import com.example.resilient_api.domain.exceptions.TechnicalErrorException;
import com.example.resilient_api.domain.model.Technology;
import com.example.resilient_api.domain.spi.TechnologyPersistencePort;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class TechnologyPersistenceAdapter implements TechnologyPersistencePort {
    private final TechnologyRepository technologyRepository;
    private final TechnologyEntityMapper technologyEntityMapper;
    
    @Override
    public Mono<Technology> save(Technology technology) {
        return technologyRepository.save(technologyEntityMapper.toEntity(technology))
                .map(technologyEntityMapper::toModel)
                .doOnError(ex -> System.out.println("Error occurred while saving technology: " + ex.getMessage()))
                .onErrorResume(ex -> Mono.error(new TechnicalErrorException(TechnicalMessage.INTERNAL_ERROR)))
                ;
    }

    @Override
    public Mono<Boolean> existByName(String name) {
        return technologyRepository.findByName(name)
                .map(technologyEntityMapper::toModel)
                .map(technology -> true)
                .defaultIfEmpty(false)
                .doOnError(ex -> System.out.println("Error occurred while finding if exists technology: " + ex.getMessage()))
                .onErrorResume(ex -> Mono.error(new TechnicalErrorException(TechnicalMessage.INTERNAL_ERROR)))
                ;
    }
}
