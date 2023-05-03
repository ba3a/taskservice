package com.example.taskservice.advice;

import com.example.taskservice.exception.EmployeeNotActiveException;
import com.example.taskservice.exception.EmployeeNotFoundException;
import com.example.taskservice.exception.EmployeeNotSpecifiedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(EmployeeNotActiveException.class)
    public ResponseEntity<Object> handleEmployeeNotActive(EmployeeNotActiveException enae){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("message", String.format("Employee with id %d is not active and cannot be assigned", enae.getEmployeeId()));
        return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Object> handleEmployeeNotFound(EmployeeNotFoundException enf){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("message", String.format("Employee with id %d was not found", enf.getEmployeeId()));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EmployeeNotSpecifiedException.class)
    public ResponseEntity<Object> handleEmployeeNotSpecified(EmployeeNotSpecifiedException ense){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("message", "Employee id is required");
        return new ResponseEntity<>(body, HttpStatus.PRECONDITION_FAILED);
    }
}
