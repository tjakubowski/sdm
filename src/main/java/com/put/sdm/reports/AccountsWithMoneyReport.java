package com.put.sdm.reports;

import com.put.sdm.products.*;

public class AccountsWithMoneyReport implements IVisitor {
    public Product visit(Account acc) {
        if(acc.getBalance().getValue() > 0) {
            return acc;
        }
        return null;
    }
    public Product visit(CreditAccount acc) {
        if(acc.getBalance().getValue() > 0) {
            return acc;
        }
        return null;
    }
    public Product visit(Deposit dep) {
        return null;
    }
    public Product visit(Loan loa) {
        return null;
    }
}