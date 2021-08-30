package com.compassouol.product.handler;

import com.compassouol.product.handler.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandlerC {

    public ResponseEntity<ExceptionResponse> handle(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ExceptionResponse(
                        status.value(),
                        message
                ));
    }
}
