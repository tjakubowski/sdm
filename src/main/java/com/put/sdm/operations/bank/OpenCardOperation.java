package com.put.sdm.operations.bank;

import com.put.sdm.Bank;
import com.put.sdm.card.Card;
import com.put.sdm.products.BaseAccount;

public class OpenCardOperation extends BankOperation {

    protected Card card;

    public OpenCardOperation(Bank bank, BaseAccount account) {
        super(bank, "Open card");
        this.card = new Card(account);
    }

    public void execute() {
        super.execute();

        this.bank.addCard(this.card);

        this.bank.addOperation(this);
    }
}
