package com.example.resilient_api.domain.usecase;

import com.example.resilient_api.domain.api.TechnologyServicePort;
import com.example.resilient_api.domain.enums.TechnicalMessage;
import com.example.resilient_api.domain.exceptions.BadRequestException;
import com.example.resilient_api.domain.exceptions.BusinessException;
import com.example.resilient_api.domain.exceptions.ProcessingErrorException;
import com.example.resilient_api.domain.exceptions.TechnicalErrorException;
import com.example.resilient_api.domain.model.Technology;
import com.example.resilient_api.domain.spi.TechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyUseCase implements TechnologyServicePort {
    private final TechnologyPersistencePort technologyPersistencePort;
    
    @Override
    public Mono<Long> registerTechnology(Technology technology) {
        return Mono.just(technology)
                .flatMap(this::validateTechnologyFields)
                .flatMap(tech -> technologyPersistencePort.existByName(tech.getName()))
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new ProcessingErrorException(TechnicalMessage.NAME_ALREADY_EXISTS)))
                .flatMap(exists -> technologyPersistencePort.save(technology))
                .map(Technology::getId)
                .doOnError(ex-> System.out.println("Error occurred while registering technology: " + ex.getMessage()))
                .onErrorResume(ex -> Mono.error(new TechnicalErrorException(TechnicalMessage.INTERNAL_ERROR)))
                ;
    }

    private Mono<Technology> validateTechnologyFields(Technology technology) {
        if (technology.getName() == null || technology.getName().trim().isEmpty()
                || technology.getDescription() == null || technology.getDescription().trim().isEmpty()) {
            return Mono.error(new BadRequestException(TechnicalMessage.INVALID_PARAMETERS));
        }
        return Mono.just(technology);
    }
    
    
}
