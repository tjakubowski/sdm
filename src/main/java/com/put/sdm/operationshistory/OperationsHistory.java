package com.put.sdm.operationshistory;

import com.put.sdm.operations.Operation;

import java.util.ArrayList;

public class OperationsHistory extends Operation {
    protected ArrayList<Operation> operations = new ArrayList<Operation>();

    public OperationsHistory(String description) {
        super(description);
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }

    public ArrayList<Operation> getOperationHistory() {
        return this.operations;
    }
}
