package com.example.resilient_api.infrastructure.entrypoints.mapper;

import com.example.resilient_api.domain.model.Technology;
import com.example.resilient_api.infrastructure.entrypoints.dto.request.TechnologyCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TechnologyEntryPointMapper {
    Technology toTechnology(TechnologyCreateRequest request);
}
