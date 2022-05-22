package com.put.sdm.operations.card;

import com.put.sdm.Bank;
import com.put.sdm.card.Card;
import com.put.sdm.products.object.Balance;

public class MakeCardPayment extends CardOperation{

    protected Balance payment;

    public MakeCardPayment(Card card, Balance payment) {
        super(card, "Card payment");

        this.payment = payment;
    }

    public void execute() {
        super.execute();

        this.card.getConnectedAccount().decreaseBalance(payment);

        this.card.getConnectedAccount().addOperation(this);
    }
}
