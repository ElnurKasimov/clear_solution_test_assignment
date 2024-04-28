package com.clearsolution.testAssignment.exception;

import com.clearsolution.testAssignment.model.Error;
import jakarta.servlet.http.HttpServletRequest;
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
public class ApplicationExceptionHandler {
    @ExceptionHandler({BindException.class,
                       NullEntityReferenceException.class,
                       FieldValidationException.class,
                       AgeRestrictionException.class,
                       DateRestrictionException.class,
                       NoHandlerFoundException.class
                       })

    public ResponseEntity<?> handleException(Exception ex, HttpServletRequest request) {
        Map<String, List<Error>> errors = new HashMap<>();
        List<Error> errorList = new ArrayList<>();
        String path = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            path += "?" + queryString;
        }
        if (ex instanceof NullEntityReferenceException) {
            Error error = new Error(400, "user", ex.getMessage(), path);
            errorList.add(error);
        }
        if (ex instanceof FieldValidationException) {
            Error error = new Error(400, "birthDate", ex.getMessage(), path);
            errorList.add(error);
        }

        if (ex instanceof AgeRestrictionException) {
            Error error = new Error(400, "birthDate", ex.getMessage(), path);
            errorList.add(error);
        }
        if (ex instanceof DateRestrictionException) {
            Error error = new Error(400, "date", ex.getMessage(), path);
            errorList.add(error);
        }
        if (ex instanceof NoHandlerFoundException) {
            Error error = new Error(404, null, ex.getMessage(), path);
            errorList.add(error);
        }
        if (ex instanceof BindException) {
            for (FieldError fieldError : ((BindException) ex).getFieldErrors()) {
                Error error = new Error(400, fieldError.getField(), fieldError.getDefaultMessage(), path);
                errorList.add(error);
            }
        }
        errors.put("errors", errorList);
        return ResponseEntity.badRequest().body(errors);

    }


}
