package br.com.gustavo.altenchallenge.exception;

import br.com.gustavo.altenchallenge.exception.handler.annotation.ExceptionMetadata;

@ExceptionMetadata(errorCode = 1003, httpStatus = 422)
public class RoomAlreadyReservedException extends RuntimeException {
    public RoomAlreadyReservedException(String message) {
        super(message);
    }
}
