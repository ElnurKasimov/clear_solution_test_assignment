package com.clearsolution.testAssignment.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler { // consider extending ResponseEntityExceptionHandler
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException ex) {
        log.error("ERROR - "+ ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);

    }

    @ExceptionHandler(NullEntityReferenceException.class)
    public ResponseEntity<?> handleNullEntityReferenceException(NullEntityReferenceException ex) {
        log.error("ERROR - "+ ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(BirthDateValidationException.class)
    public ResponseEntity<?> handleBirthDateValidationException(BirthDateValidationException ex) {
        log.error("ERROR - "+ ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(AgeRestrictionException.class)
    public ResponseEntity<?> handleAgeRestrictionException(AgeRestrictionException ex) {
        log.error("ERROR - "+ ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(NotFoundException ex) {
        log.error("ERROR - "+ ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
