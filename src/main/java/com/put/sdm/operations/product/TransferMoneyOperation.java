package com.put.sdm.operations.product;

import com.put.sdm.products.Product;
import com.put.sdm.products.object.Balance;

public class TransferMoneyOperation extends ProductOperation {

    protected Balance payment;
    protected Product receiverAccount;

    public TransferMoneyOperation(Product senderAccount, Product receiverAccount, Balance payment) {
        super(senderAccount, "Transfer payment");

        this.receiverAccount = receiverAccount;
        this.payment = payment;
    }

    public void execute() {
        super.execute();

        this.product.decreaseBalance(this.payment);
        this.receiverAccount.increaseBalance(this.payment);
    }

}
