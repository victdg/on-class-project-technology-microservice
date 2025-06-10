package com.example.resilient_api.domain.exceptions;

import com.example.resilient_api.domain.enums.TechnicalMessage;
import lombok.Getter;

@Getter
public class TechnicalErrorException extends Error {

    public TechnicalErrorException(TechnicalMessage technicalMessage) {
        super(technicalMessage.getMessage());
    }

}
