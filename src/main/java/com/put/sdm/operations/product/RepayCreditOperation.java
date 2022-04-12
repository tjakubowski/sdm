package com.put.sdm.operations.product;

import com.put.sdm.products.Account;
import com.put.sdm.products.Loan;
import com.put.sdm.products.object.Balance;

public class RepayCreditOperation extends ProductOperation {

    protected Loan loanAccount;
    protected Balance payment;

    public RepayCreditOperation(Account consumerAccount, Loan loanAccount, Balance payment) {
        super(consumerAccount, "Repay credit");

        this.loanAccount = loanAccount;
        this.payment = payment;
    }

    public void execute() {
        super.execute();

        this.product.decreaseBalance(this.payment);
        this.loanAccount.increaseBalance(this.payment);
        this.loanAccount.updateNextRepaymentDateTime();
    }

}
