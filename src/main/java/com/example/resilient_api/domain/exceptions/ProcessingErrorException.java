package com.example.resilient_api.domain.exceptions;

import com.example.resilient_api.domain.enums.TechnicalMessage;
import lombok.Getter;

@Getter
public class ProcessingErrorException extends Error {

    private final TechnicalMessage technicalMessage;

    public ProcessingErrorException(TechnicalMessage message) {
        technicalMessage = message;
    }


}
