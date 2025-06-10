package com.example.resilient_api.domain.exceptions;

import com.example.resilient_api.domain.enums.TechnicalMessage;
import lombok.Getter;

@Getter
public class BadRequestException extends Error {

    private final TechnicalMessage technicalMessage;

    public BadRequestException(TechnicalMessage message) {
        technicalMessage = message;
    }


}
