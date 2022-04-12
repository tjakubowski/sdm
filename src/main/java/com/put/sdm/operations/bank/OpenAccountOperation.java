package com.put.sdm.operations.bank;

import com.put.sdm.Bank;
import com.put.sdm.products.Account;
import com.put.sdm.products.object.Person;

public class OpenAccountOperation extends BankOperation {

    protected Account account;

    public OpenAccountOperation(Bank bank, Person owner) {
        super(bank, "Open account");
        this.account = new Account(owner);
    }

    public void execute() {
        super.execute();

        this.account.updateOpeningTime();
        this.bank.addAccount(this.account);
    }
}
