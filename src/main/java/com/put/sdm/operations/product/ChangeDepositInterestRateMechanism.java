package com.put.sdm.operations.product;

import com.put.sdm.interestrates.IInterestMechanism;
import com.put.sdm.products.Deposit;
import com.put.sdm.products.Loan;

public class ChangeDepositInterestRateMechanism extends ProductOperation {
    IInterestMechanism interestMechanism;

    public ChangeDepositInterestRateMechanism(Deposit deposit, IInterestMechanism interest) {
        super(deposit, "Changed deposit interest mechanism");

        this.interestMechanism = interest;
    }

    public void execute() {
        super.execute();

        Deposit deposit = (Deposit) this.product;

        deposit.setInterestMechanism(interestMechanism);

        deposit.addOperation(this);
    }
}
