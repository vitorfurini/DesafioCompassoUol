package com.compassouol.product.handlerexception;

import com.compassouol.product.handler.ExceptionHandlerC;
import com.compassouol.product.handler.response.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MethodArgumentExceptionHandlerController {

    @Autowired
    private ExceptionHandlerC exceptionHandlerC;
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handler(MethodArgumentNotValidException exception) {
        return exceptionHandlerC
                .handle(HttpStatus.BAD_REQUEST, parseErrors(exception));
    }

    private String parseErrors(MethodArgumentNotValidException exception) {
        return parseFieldExceptionErrors(exception)
                .concat(parseObjectExceptionErrors(exception));
    }

    private String parseFieldExceptionErrors(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors()
                .stream()
                .map(this::parseFieldExceptionError)
                .collect(Collectors.joining("; "));
    }

    private String parseFieldExceptionError(FieldError fieldError) {
        return String.format(
                "Field '%s' contain error: %s",
                fieldError.getField(),
                messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())
        );
    }

    private String parseObjectExceptionErrors(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getGlobalErrors()
                .stream()
                .map(this::parseObjectError)
                .collect(Collectors.joining("; "));
    }

    private String parseObjectError(ObjectError objectError) {
        return String.format(
                "Object %s contain error: %s",
                objectError.getObjectName(),
                messageSource.getMessage(objectError, LocaleContextHolder.getLocale())
        );
    }
}
