package com.clearsolution.testAssignment.exception;

import com.clearsolution.testAssignment.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler { // consider extending ResponseEntityExceptionHandler
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException ex) {
        List<Error> errorList = new ArrayList<>();
        Map<String, List<Error>> errors = new HashMap<>();
        for (FieldError fieldError : ex.getFieldErrors()) {
            Error error = new Error(400, fieldError.getField(), fieldError.getDefaultMessage());
            errorList.add(error);
        }
        errors.put("errors", errorList);
        return ResponseEntity.badRequest().body(errors);

    }

    @ExceptionHandler(NullEntityReferenceException.class)
    public ResponseEntity<?> handleNullEntityReferenceException(NullEntityReferenceException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<?> handleBirthDateValidationException(FieldValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(AgeRestrictionException.class)
    public ResponseEntity<?> handleAgeRestrictionException(AgeRestrictionException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
