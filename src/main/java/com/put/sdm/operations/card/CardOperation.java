package com.put.sdm.operations.card;

import com.put.sdm.Bank;
import com.put.sdm.card.Card;
import com.put.sdm.operations.Operation;

public class CardOperation extends Operation {

    protected Card card;

    public CardOperation(Card card, String description) {
        super(description);
        this.card = card;
    }

}