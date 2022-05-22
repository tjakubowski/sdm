package com.put.sdm.products;

import com.put.sdm.operationshistory.OperationsHistory;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;
import com.put.sdm.reports.IElement;
import com.put.sdm.reports.IVisitor;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Product extends OperationsHistory implements IProduct, IElement {

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

    public String getId()
    {
        return this.id;
    }

    public boolean decreaseBalance(Balance payment) {
        if(payment.getValue() > this.balance.getValue()) {
            System.out.println("Payment greater than balance");
            return false;
        }

        this.balance.decrease(payment);

        return true;
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

    public abstract Product accept(IVisitor visitor);
}
