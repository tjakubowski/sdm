package com.put.sdm.operations.product;

import com.put.sdm.products.Account;
import com.put.sdm.products.Deposit;
import com.put.sdm.products.object.Balance;

public class MakeDepositOperation extends ProductOperation {

    protected Deposit deposit;
    protected Balance payment;

    public MakeDepositOperation(Account senderAccount, Deposit deposit, Balance payment) {
        super(senderAccount, "Make deposit");
        this.deposit = deposit;
        this.payment = payment;
    }

    public void execute() {
        super.execute();
        this.product.decreaseBalance(this.payment);
        this.deposit.increaseBalance(this.payment);
    }

}
