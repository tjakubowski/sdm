package com.put.sdm.products;

import com.put.sdm.operations.Operation;
import com.put.sdm.operations.product.TransferMoneyOperation;
import com.put.sdm.operationshistory.OperationsHistory;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;
import com.put.sdm.reports.IReportable;

import java.time.LocalDateTime;
import java.util.UUID;

public class Product extends OperationsHistory implements IProduct {

    protected String id;

    protected Person owner;

    protected Balance balance;

    protected LocalDateTime openingDateTime;

    public Product(Person owner) {
        super("Product instance");

        this.id = UUID.randomUUID().toString();
        this.owner = owner;
        this.balance = new Balance();
        this.openingDateTime = LocalDateTime.now();
    }

    public void decreaseBalance(Balance payment) {
        if(payment.getValue() > this.balance.getValue()) {
            System.out.println("Payment greater than balance");
            return;
        }

        this.balance.decrease(payment);
    }

    public void increaseBalance(Balance payment) {
        this.balance.increase(payment);
    }

    public Balance getBalance() {
        return balance;
    }

    public Person getOwner() {
        return owner;
    }

    public LocalDateTime getOpeningDateTime() {
        return openingDateTime;
    }

    public void updateOpeningTime() {
        if(this.executionDateTime != null) {
            System.out.println("Cannot update previously updated opening time");
            return;
        }

        this.openingDateTime = LocalDateTime.now();
    }

    public String accept(IReportable visitor) {
        return visitor.visitProduct(this);
    }
}
