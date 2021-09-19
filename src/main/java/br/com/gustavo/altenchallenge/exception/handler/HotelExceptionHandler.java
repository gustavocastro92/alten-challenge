package br.com.gustavo.altenchallenge.exception.handler;

import br.com.gustavo.altenchallenge.exception.handler.annotation.ExceptionMetadata;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HotelExceptionHandler {

    private static final String DEFAULT_MESSAGE = "Service temporary unavailable";
    private static final int DEFAULT_ERROR_CODE = 1000;
    private static final int DEFAULT_HTTP_STATUS = 500;
    private static final String MESSAGE_TEMPLATE = "{\"message\":\"%s\",\"errorCode\":\"%s\"}";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        ExceptionMetadata metadata = getExceptionMetadata(ex);

        if (metadata != null) {
            return formatResponseWithMetadata(ex, metadata);
        }
        return buildDefaultResponse();
    }

    private ResponseEntity<?> formatResponseWithMetadata(Exception ex, ExceptionMetadata metadata) {
        try {
            String message = String.format(MESSAGE_TEMPLATE, ex.getMessage(), metadata.errorCode());
            return this.buildReturn(message, metadata.httpStatus());
        } catch (Exception e) {
            return buildDefaultResponse();
        }
    }

    private ResponseEntity<?> buildDefaultResponse() {
        String message = String.format(MESSAGE_TEMPLATE, DEFAULT_MESSAGE, DEFAULT_ERROR_CODE);
        return this.buildReturn(message, DEFAULT_HTTP_STATUS);
    }

    private ResponseEntity<?> buildReturn(String message, int httpStatus) {
        return new ResponseEntity<>(message, null, HttpStatus.valueOf(httpStatus));
    }

    private ExceptionMetadata getExceptionMetadata(Exception ex) {
        return AnnotationUtils.findAnnotation(ex.getClass(), ExceptionMetadata.class);
    }


}
