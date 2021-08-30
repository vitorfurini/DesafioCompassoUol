package com.compassouol.product.handler;

import com.compassouol.product.handler.response.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @Autowired
    private ExceptionHandlerC exceptionHandler;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handle(Exception exception) {
        exception.printStackTrace();
        return exceptionHandler
                .handle(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown server error. Please, try again later.");
    }
}
