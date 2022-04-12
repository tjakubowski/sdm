package com.put.sdm.operations.product;

import com.put.sdm.products.Account;
import com.put.sdm.products.Deposit;
import com.put.sdm.products.object.Balance;

public class WithdrawDepositOperation extends ProductOperation {

    protected Deposit deposit;
    protected Balance payment;

    public WithdrawDepositOperation(Account receiverAccount, Deposit deposit, Balance payment) {
        super(receiverAccount, "Withdraw from deposit");

        this.deposit = deposit;
        this.payment = payment;
    }

    public void execute() {
        super.execute();

        this.product.increaseBalance(this.payment);
        this.deposit.decreaseBalance(this.payment);
    }

}
