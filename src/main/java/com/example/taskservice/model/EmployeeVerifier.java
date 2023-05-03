package com.example.taskservice.model;

import com.example.taskservice.adapter.EmployeeAdapter;
import com.example.taskservice.exception.EmployeeNotActiveException;
import com.example.taskservice.exception.EmployeeNotSpecifiedException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeVerifier {
    private final EmployeeAdapter employeeAdapter;
    @PrePersist
    @PreUpdate
    public void verifyEmployee(Task task){
        if(null == task.getAssigneeId()){
            throw new EmployeeNotSpecifiedException();
        }
        if (!employeeAdapter.isEmployeeActive(task.getAssigneeId())) {
            throw new EmployeeNotActiveException(task.getAssigneeId());
        }
    }
}
