package com.compassouol.product.handlerexception;

import com.compassouol.product.handler.ExceptionHandlerC;
import com.compassouol.product.handler.response.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseStatusExceptionControllerHandler {

    @Autowired
    private ExceptionHandlerC exceptionHandlerC;

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handler(ResponseStatusException exception) {
        return exceptionHandlerC
                .handle(exception.getStatus(), exception.getReason());
    }
}
