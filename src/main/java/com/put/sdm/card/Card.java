package com.put.sdm.card;

import com.put.sdm.products.BaseAccount;

public class Card {
    protected BaseAccount connected_account;

    public BaseAccount getConnectedAccount(){
        return connected_account;
    }

    public Card(BaseAccount account){
        this.connected_account = account;
    }
}
