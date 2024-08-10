package com.example.food_ordering.config;


import com.example.food_ordering.error.ActionException;
import com.example.food_ordering.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 當觸發ActionException的時候，自動捕捉
 */
@RestControllerAdvice
public class ActionExceptionHandler {
    @ExceptionHandler(ActionException.class)
    public ResponseEntity<ErrorResponse> handleActionException (ActionException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
