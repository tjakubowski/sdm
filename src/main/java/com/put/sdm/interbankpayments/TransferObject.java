package com.put.sdm.interbankpayments;

import com.put.sdm.Bank;
import com.put.sdm.products.object.Balance;

public class TransferObject {

    protected Bank originBank;

    protected Bank destinationBank;

    protected Balance payment;

    public TransferObject(Bank originBank, Bank destinationBank, Balance payment) {
        this.originBank = originBank;
        this.destinationBank = destinationBank;
        this.payment = payment;
    }

    public Bank getOriginBank() {
        return originBank;
    }

    public Bank getDestinationBank() {
        return destinationBank;
    }

    public Balance getPayment() {
        return payment;
    }
}
