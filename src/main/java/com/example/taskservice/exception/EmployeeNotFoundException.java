package com.example.taskservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmployeeNotFoundException extends RuntimeException{
    private final Long employeeId;
}
