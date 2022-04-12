package com.put.sdm.operations;

import java.time.LocalDateTime;

public class Operation {

    protected String description;

    protected LocalDateTime executionDateTime;

    public Operation(String description) {
        this.description = description;
        this.executionDateTime = null;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getExecutionDateTime() {
        return this.executionDateTime;
    }

    public void execute() {
        if(this.executionDateTime != null) {
            System.out.println("Cannot execute already executed operation");
            return;
        }

        this.executionDateTime = LocalDateTime.now();
    }
}
