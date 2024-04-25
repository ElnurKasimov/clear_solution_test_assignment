package com.clearsolution.testAssignment.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404(HttpServletRequest request, Exception e) {
        return getModelAndView(request, HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNotFoundException(HttpServletRequest request, EntityNotFoundException e) {
        return getModelAndView(request, HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(NullEntityReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNullEntityReferenceException(HttpServletRequest request, NullEntityReferenceException e) {
        return getModelAndView(request, HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleOtherExceptions(HttpServletRequest request, Exception e) {
        return getModelAndView(request, HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    public ModelAndView getModelAndView(HttpServletRequest request, HttpStatus httpStatus, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        switch(httpStatus) {
            case BAD_REQUEST:
                modelAndView.setViewName("error");
                break;
            case NOT_FOUND:
                modelAndView.setViewName("404");
                break;
            case INTERNAL_SERVER_ERROR:
                modelAndView.setViewName("500");
                break;
            default:
                modelAndView.setViewName("unknown-error");
                break;
        }
        modelAndView.addObject("title", httpStatus.getReasonPhrase() );
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("localDateTime", LocalDateTime.now());
        modelAndView.addObject("status", httpStatus.value());
        modelAndView.addObject("type", e.getClass().getName());
        String debugPropertyValue = System.getProperty("debug");
        String debugEnvValue = System.getenv("DEBUG");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        if( (debugPropertyValue != null && debugPropertyValue.equals("true")) ||
                (debugEnvValue != null && debugEnvValue.equals("true")) ) {
            modelAndView.addObject("stackTrace", stackTrace);
        }
        return modelAndView;
    }

}
