package com.crm1.exception;


import com.crm1.payLoad.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

// global catch block where ever exception occurs it will directly comes here
@ControllerAdvice
public class HandelException {

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handleEmployeeNotFoundException(

            ResourceNotFound e, WebRequest request)
    {
        ErrorDetails errorDetail = new ErrorDetails(
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR) ;


    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalException(

            Exception e, WebRequest request)
    {
        ErrorDetails errorDetail = new ErrorDetails(
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR) ;


    }


}
