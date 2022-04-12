package com.put.sdm.operations.bank;

import com.put.sdm.Bank;
import com.put.sdm.products.object.Balance;

public class InterBankPaymentOperation extends BankOperation {

    protected Bank receiverBank;
    protected Balance payment;

    public InterBankPaymentOperation(Bank senderBank, Bank receiverBank, Balance payment) {
        super(senderBank, "Inter bank payment");

        this.receiverBank = receiverBank;
        this.payment = payment;
    }

    public void execute() {
        super.execute();

        this.bank.decreaseBalance(this.payment);
        this.receiverBank.increaseBalance(this.payment);
    }

}
