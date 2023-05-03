package com.example.taskservice.model;

import com.example.taskservice.domain.Priority;
import com.example.taskservice.domain.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners({AuditingEntityListener.class, EmployeeVerifier.class})
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    private Instant modifiedAt;
    private String title;
    private String description;
    private Instant dueDate;
    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private Priority priority = Priority.LOW;
    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private Status status = Status.TO_DO;
    private Long assigneeId;
}
