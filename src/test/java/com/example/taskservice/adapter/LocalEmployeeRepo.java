package com.example.taskservice.adapter;

import com.example.taskservice.exception.EmployeeNotFoundException;
import lombok.Builder;
import lombok.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
@Profile("local")
public class LocalEmployeeRepo implements EmployeeAdapter {
    private final Map<Long, Employee> employees = new TreeMap<>();

    {
        employees.put(1L, EmployeeImpl.builder().id(1L).isActive(true).build());
        employees.put(2L, EmployeeImpl.builder().id(2L).isActive(true).build());
        employees.put(3L, EmployeeImpl.builder().id(3L).isActive(true).build());
        employees.put(4L, EmployeeImpl.builder().id(4L).isActive(false).build());
        employees.put(5L, EmployeeImpl.builder().id(5L).isActive(false).build());
    }

    @Override
    public Boolean isEmployeeActive(Long id) {
        Employee employee = employees.get(id);
        if (null != employee) {
            return employee.isActive();
        }
        throw new EmployeeNotFoundException(id);
    }

    @Value
    @Builder
    static class EmployeeImpl implements Employee {
        Long id;
        boolean isActive;
    }
}
