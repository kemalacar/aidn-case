package com.aid.controller;

import com.aid.log.LogThreadLocal;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * Contains the Exception Handlers for the REST API.
 *
 * @author Kemal Acar
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Handles IllegalArgumentExceptions.
     *
     * @param ex      exception
     * @param request a request created by the user.
     * @return prepared exception response.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleRemoteServiceException(IllegalArgumentException ex, WebRequest request) {

        log.error("Exception Handled, Type: IllegalArgumentException. txID=" + LogThreadLocal.get().getTxId(), ex);

        var exceptionOutput = ExceptionOutput.builder()
                .message("Server Exception!")
                .detail(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, exceptionOutput, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Generic exception.
     *
     * @param ex      any Exception.
     * @param request a request created by the user.
     * @return prepared exception response.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleGeneralExceptions(Exception ex, WebRequest request) {
        log.error("Exception Handled, Type: IllegalArgumentException. txID=" + LogThreadLocal.get().getTxId(), ex);

        var exceptionOutput = ExceptionOutput.builder()
                .message("Server Exception!")
                .detail("We are unable to process your transaction at the moment. Would you please try again later?")
                .build();

        return handleExceptionInternal(ex, exceptionOutput, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Handles validation exceptions.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception Handled, Type: FieldValidation txID=" + LogThreadLocal.get().getTxId(), ex);

        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> "'" + error.getField() + "' field is wrong. " + error.getDefaultMessage())
                .toList();

        return handleExceptionInternal(ex, ExceptionOutput.builder().message("Invalid Request").detail(details.toString()).build(), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class ExceptionOutput {
        private String message;
        private String detail;
    }
}
