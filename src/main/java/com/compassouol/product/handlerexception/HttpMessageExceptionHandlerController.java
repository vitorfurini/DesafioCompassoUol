package com.compassouol.product.handlerexception;

import com.compassouol.product.handler.ExceptionHandlerC;
import com.compassouol.product.handler.response.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HttpMessageExceptionHandlerController {

    @Autowired
    private ExceptionHandlerC exceptionHandlerC;

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpMessageConversionException exception) {
        return exceptionHandlerC
                .handle(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
