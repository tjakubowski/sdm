package com.put.sdm.operations;

import java.time.LocalDateTime;

public interface IOperation {

    String getDescription();

    LocalDateTime getExecutionDateTime();

    void execute();

}
