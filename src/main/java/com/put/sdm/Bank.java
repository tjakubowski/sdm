package com.put.sdm;

import com.put.sdm.operations.Operation;
import com.put.sdm.operations.bank.OpenAccountOperation;
import com.put.sdm.operationshistory.OperationsHistory;
import com.put.sdm.products.Account;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;

import java.util.ArrayList;

public class Bank extends OperationsHistory {

    protected ArrayList<Account> accounts;

    protected Balance balance;

    public Bank() {
        super("Open bank");

        this.balance = new Balance();
    }

    public void openAccountForPerson(Person owner)
    {
        Operation operation = new OpenAccountOperation(this, owner);
        operation.execute();
        this.addOperation(operation);
    }

    public void addAccount(Account account)
    {
        this.accounts.add(account);
    }

    public void increaseBalance(Balance payment)
    {
        this.balance.credit(payment);
    }

    public void decreaseBalance(Balance payment)
    {
        this.balance.debit(payment);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Balance getBalance() {
        return balance;
    }
}
