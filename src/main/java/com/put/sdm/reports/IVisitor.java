package com.put.sdm.reports;

import com.put.sdm.products.*;

public interface IVisitor {
    Product visit(Account acc);
    Product visit(CreditAccount acc);
    Product visit(Deposit acc);
    Product visit(Loan acc);
}
