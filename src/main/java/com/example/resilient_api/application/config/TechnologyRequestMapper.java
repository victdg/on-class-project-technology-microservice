package com.example.resilient_api.application.config;

import com.example.resilient_api.infrastructure.entrypoints.dto.request.TechnologyCreateRequest;
import com.example.resilient_api.domain.model.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TechnologyRequestMapper {
    @Mapping(target = "id", ignore = true)
    Technology toModel(TechnologyCreateRequest technologyCreateRequest);
}
