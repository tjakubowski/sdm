package com.put.sdm.reports;

import com.put.sdm.products.*;

public class HistoryReport implements IVisitor {
    public Product visit(Account acc) {
        if(acc.getOperationHistory().size() > 0) {
            return acc;
        }
        return null;
    }
    public Product visit(CreditAccount acc) {
        if(acc.getOperationHistory().size() > 0) {
            return acc;
        }
        return null;
    }
    public Product visit(Deposit dep) {
        if(dep.getOperationHistory().size() > 0) {
            return dep;
        }
        return null;
    }
    public Product visit(Loan loa) {
        if(loa.getOperationHistory().size() > 0) {
            return loa;
        }
        return null;
    }
}
