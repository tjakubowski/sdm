package com.put.sdm.products;

import com.put.sdm.interestrates.IInterestMechanism;
import com.put.sdm.operations.Operation;
import com.put.sdm.operations.product.MakeDepositOperation;
import com.put.sdm.operations.product.WithdrawDepositOperation;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;

import java.time.LocalDateTime;

public class Deposit extends Product {

    protected Person supplierAccount;

    protected IInterestMechanism interestRate;

    protected LocalDateTime closingDateTime;

    public Deposit(Person owner, IInterestMechanism interestRate) {
        super(owner);
        this.supplierAccount = owner;
        this.interestRate = interestRate;
    }

    public void supplyWithMoney(Balance money) {
        Operation operation = new MakeDepositOperation(new Account(this.supplierAccount), this, money);
        this.addOperation(operation);
    }

    public void withdrawMoney(Balance money) {
        Operation operation = new WithdrawDepositOperation(new Account(this.supplierAccount), this, money);
        this.addOperation(operation);
    }

    public LocalDateTime getClosingDateTime() {
        return closingDateTime;
    }

    public void updateOpeningTime() {
        super.updateOpeningTime();

        this.closingDateTime = this.openingDateTime;
    }
}
