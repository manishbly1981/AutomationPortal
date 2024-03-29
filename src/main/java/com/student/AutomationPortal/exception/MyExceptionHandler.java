package com.student.AutomationPortal.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorMessage> handleAllExceptions (Exception e, WebRequest request){
        ErrorMessage errorMessage= new ErrorMessage(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

/*******************************CUSTOM EXCEPTION*****************************************************/
//@ControllerAdvice
//public class MyExceptionHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(UserServiceException.class)
//    public final ResponseEntity<ErrorMessage> handleSpecificExceptions(Exception ex, WebRequest request) {
//        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage(),
//                request.getDescription(false));
//        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}