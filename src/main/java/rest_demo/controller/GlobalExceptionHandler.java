package rest_demo.controller;

import org.springframework.web.bind.annotation.ResponseStatus;
import rest_demo.exception.BadRequestException;
import rest_demo.exception.ProviderNotFoundException;
import rest_demo.exception.UnauthorizedException;
import rest_demo.exception.UserNotFoundException;
import rest_demo.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // global exception controller
public class GlobalExceptionHandler {

    /** 400 Bad Request Exception **/
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandlerBadRequest(Exception e) {
        return ErrorResponse.builder().errorCode(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build();
    }

    /** 401 Unauthorized Exception **/
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse exceptionHandlerUnauthorized(Exception e) {
        return ErrorResponse.builder().errorCode(HttpStatus.UNAUTHORIZED.value()).message(e.getMessage()).build();
    }

    /** 404 Not Found Exception **/
    @ExceptionHandler(ProviderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse exceptionHandlerProviderNotFound(Exception e) {
        return ErrorResponse.builder().errorCode(HttpStatus.NOT_FOUND.value()).message(e.getMessage()).build();
    }
}
