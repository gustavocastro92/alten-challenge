package br.com.gustavo.altenchallenge.exception.handler.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.TYPE})
public @interface ExceptionMetadata {
    int httpStatus() default 500;
    int errorCode() default 1000;
}
