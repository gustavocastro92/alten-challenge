package br.com.gustavo.altenchallenge.exception;

import br.com.gustavo.altenchallenge.exception.handler.annotation.ExceptionMetadata;

@ExceptionMetadata(errorCode = 1004, httpStatus = 422)
public class PeriodExceedsException extends RuntimeException {
    public PeriodExceedsException(String message) {
        super(message);
    }
}
