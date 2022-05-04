package com.put.sdm.operations.bank;

import com.put.sdm.Bank;
import com.put.sdm.card.Card;
import com.put.sdm.operations.Operation;
import com.put.sdm.products.Account;
import com.put.sdm.products.BaseAccount;
import com.put.sdm.products.object.Person;

public class OpenCardOperation extends BankOperation {

    protected Card card;

    public OpenCardOperation(Bank bank, BaseAccount account) {
        super(bank, "Open card");
        this.card = new Card(account);
    }

    public void execute() {
        super.execute();

        this.bank.addCard(this.card);
    }
}
