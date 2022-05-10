package com.put.sdm.operations.bank;

import com.put.sdm.Bank;
import com.put.sdm.products.DebitAccount;
import com.put.sdm.products.object.Person;

public class OpenDebitAccountOperation extends BankOperation {

    protected DebitAccount account;

    public OpenDebitAccountOperation(Bank bank, Person owner) {
        super(bank, "Open debit account");
        this.account = new DebitAccount(owner);
    }

    public void execute() {
        super.execute();

        this.account.updateOpeningTime();
        this.bank.addAccount(this.account);
    }
}
