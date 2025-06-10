package com.example.resilient_api.infrastructure.entrypoints.handler;

import com.example.resilient_api.domain.api.TechnologyServicePort;
import com.example.resilient_api.domain.enums.TechnicalMessage;
import com.example.resilient_api.domain.exceptions.BadRequestException;
import com.example.resilient_api.domain.exceptions.ProcessingErrorException;
import com.example.resilient_api.domain.exceptions.TechnicalErrorException;
import com.example.resilient_api.infrastructure.entrypoints.dto.request.TechnologyCreateRequest;
import com.example.resilient_api.infrastructure.entrypoints.mapper.TechnologyEntryPointMapper;
import com.example.resilient_api.infrastructure.entrypoints.util.APIResponse;
import com.example.resilient_api.infrastructure.entrypoints.util.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@Component
@RequiredArgsConstructor
public class TechnologyHandler {
    private final TechnologyServicePort technologyServicePort;
    private final TechnologyEntryPointMapper technologyEntryPointMapper;

    public Mono<ServerResponse> saveTechnology(ServerRequest request){
        return request.bodyToMono(TechnologyCreateRequest.class)
                .map(technology -> {
                    System.out.println("Received technology: " + technology);
                    return technology;
                })
                .flatMap(technology -> technologyServicePort.registerTechnology(
                        technologyEntryPointMapper.toTechnology(technology)))
                .flatMap(savedTechnology -> ServerResponse.status(HttpStatus.CREATED).bodyValue(Map.ofEntries(entry("id", savedTechnology))))
                .doOnError(ex -> System.out.println("Error occurred while saving technology: " + ex.getMessage()))
                .onErrorResume(BadRequestException.class, ex -> buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getTechnicalMessage()))
                .onErrorResume(ProcessingErrorException.class, ex -> buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getTechnicalMessage()))
                .onErrorResume(TechnicalErrorException.class, ex -> buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, TechnicalMessage.INTERNAL_ERROR))
                .onErrorResume(ex -> buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, TechnicalMessage.INTERNAL_ERROR))
        ;
    }

    private Mono<ServerResponse> buildErrorResponse(HttpStatus httpStatus, TechnicalMessage error) {
        return Mono.defer(() -> {
            APIResponse apiErrorResponse = APIResponse
                    .builder()
                    .code(error.getCode())
                    .message(error.getMessage())
                    .build();
            return ServerResponse.status(httpStatus)
                    .bodyValue(apiErrorResponse);
        });
    }
}
