package com.put.sdm.operations.product;

import com.put.sdm.interestrates.IInterestMechanism;
import com.put.sdm.products.Loan;

public class ChangeLoanInterestRateMechanism extends ProductOperation {
    IInterestMechanism interestMechanism;

    public ChangeLoanInterestRateMechanism(Loan loan, IInterestMechanism interest) {
        super(loan, "Changed loan interest mechanism");

        this.interestMechanism = interest;
    }

    public void execute() {
        super.execute();

        Loan loan = (Loan) this.product;

        loan.setInterestMechanism(interestMechanism);

        loan.addOperation(this);
    }
}
