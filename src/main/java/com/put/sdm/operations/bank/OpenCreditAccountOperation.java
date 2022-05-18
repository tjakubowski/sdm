package com.put.sdm.operations.bank;

import com.put.sdm.Bank;
import com.put.sdm.products.CreditAccount;
import com.put.sdm.products.DebitAccount;
import com.put.sdm.products.object.Person;

public class OpenCreditAccountOperation extends BankOperation {

    protected CreditAccount account;

    public OpenCreditAccountOperation(Bank bank, Person owner) {
        super(bank, "Open credit account");
        this.account = new CreditAccount(new DebitAccount(owner));
    }

    public void execute() {
        super.execute();

        this.account.updateOpeningTime();
        this.bank.addAccount(this.account);
    }
}
