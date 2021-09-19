package br.com.gustavo.altenchallenge.exception;

import br.com.gustavo.altenchallenge.exception.handler.annotation.ExceptionMetadata;

@ExceptionMetadata(errorCode = 1002, httpStatus = 404)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
