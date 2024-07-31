package com.ecommerce.plantme.exceptions;

import com.ecommerce.plantme.payloads.CommonApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonApiException.class)
    public ResponseEntity<CommonApiExceptionResponse> handleCommonApiException (CommonApiException e) {
        String message = e.getMessage();
        CommonApiExceptionResponse commonApiExceptionResponse = new CommonApiExceptionResponse();
        commonApiExceptionResponse.setStatus(HttpStatus.NOT_FOUND.value());
        commonApiExceptionResponse.setMessage(e.getMessage());
        commonApiExceptionResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<CommonApiExceptionResponse>(commonApiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonApiExceptionResponse> myResourceNotFoundException(ResourceNotFoundException e) {
        String message = e.getMessage();
        CommonApiExceptionResponse commonApiExceptionResponse = new CommonApiExceptionResponse();
        commonApiExceptionResponse.setStatus(HttpStatus.NOT_FOUND.value());
        commonApiExceptionResponse.setMessage(e.getMessage());
        commonApiExceptionResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<CommonApiExceptionResponse>(commonApiExceptionResponse, HttpStatus.NOT_FOUND);
    }
}
