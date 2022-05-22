package com.put.sdm.reports;

import com.put.sdm.products.*;

public class CompleteReport implements IVisitor {
    public Product visit(Account acc) {
        return acc;
    }
    public Product visit(CreditAccount acc) {
        return acc;
    }
    public Product visit(Deposit dep) {
        return dep;
    }
    public Product visit(Loan loa) {
        return loa;
    }
}
