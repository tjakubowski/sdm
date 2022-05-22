package com.put.sdm.operations.product;

import com.put.sdm.operations.Operation;
import com.put.sdm.products.Loan;
import com.put.sdm.products.object.Balance;

public class IncreaseCreditByInterest extends ProductOperation {
    public IncreaseCreditByInterest(Loan loan) {
        super(loan, "Credit increased by interest");
    }

    public void execute() {
        super.execute();

        Loan loan = (Loan) this.product;

        loan.increaseCreditByInterest();

        loan.addOperation(this);
    }
}
