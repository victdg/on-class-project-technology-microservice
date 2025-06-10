package com.example.resilient_api.infrastructure.adapters.technology;

import com.example.resilient_api.domain.model.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TechnologyEntityMapper {
    Technology toModel(TechnologyEntity technologyEntity);
    TechnologyEntity toEntity(Technology technology);
}
