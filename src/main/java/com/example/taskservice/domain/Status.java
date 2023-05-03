package com.example.taskservice.domain;

public enum Status implements CompletionAware {
    TO_DO,
    IN_PROGRESS,
    CANCELLED,
    DONE,
    ;

    @Override
    public boolean isCompleted() {
        return equals(DONE);
    }
}
