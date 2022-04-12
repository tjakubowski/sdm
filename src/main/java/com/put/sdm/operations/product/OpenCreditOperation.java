package com.put.sdm.operations.product;

import com.put.sdm.products.Account;
import com.put.sdm.products.Loan;
import com.put.sdm.products.object.Balance;

public class OpenCreditOperation extends ProductOperation {

    protected Loan loanAccount;
    protected Balance credit;

    public OpenCreditOperation(Account consumerAccount, Loan loanAccount, Balance credit) {
        super(consumerAccount, "Open credit");
        this.loanAccount = loanAccount;
        this.credit = credit;
    }

    public void execute() {
        super.execute();
        this.loanAccount.decreaseBalance(this.credit);
        this.product.increaseBalance(this.credit);
    }

}
