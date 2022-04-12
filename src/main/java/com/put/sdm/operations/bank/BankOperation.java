package com.put.sdm.operations.bank;

import com.put.sdm.Bank;
import com.put.sdm.operations.Operation;

public class BankOperation extends Operation {

    protected Bank bank;

    public BankOperation(Bank bank, String description) {
        super(description);
        this.bank = bank;
    }

}
