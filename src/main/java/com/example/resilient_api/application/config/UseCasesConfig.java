package com.example.resilient_api.application.config;

import com.example.resilient_api.domain.api.TechnologyServicePort;
import com.example.resilient_api.domain.spi.TechnologyPersistencePort;
import com.example.resilient_api.domain.usecase.TechnologyUseCase;
import com.example.resilient_api.infrastructure.adapters.technology.TechnologyEntityMapper;
import com.example.resilient_api.infrastructure.adapters.technology.TechnologyPersistenceAdapter;
import com.example.resilient_api.infrastructure.adapters.technology.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {
        private final TechnologyEntityMapper technologyEntityMapper;

        private final TechnologyRepository technologyRepository;

        @Bean
        public TechnologyPersistencePort technologyPersistencePort() {
                return new TechnologyPersistenceAdapter(technologyRepository, technologyEntityMapper);
        }

        @Bean
        public TechnologyServicePort technologyServicePort(TechnologyPersistencePort technologyPersistencePort) {
                return new TechnologyUseCase(technologyPersistencePort());
        }

        }
