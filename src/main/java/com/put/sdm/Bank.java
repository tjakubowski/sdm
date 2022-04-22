package com.put.sdm;

import com.put.sdm.operations.Operation;
import com.put.sdm.operations.bank.OpenAccountOperation;
import com.put.sdm.operations.bank.OpenDebitAccountOperation;
import com.put.sdm.operationshistory.OperationsHistory;
import com.put.sdm.products.Account;
import com.put.sdm.products.BaseAccount;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;

import java.util.ArrayList;

public class Bank extends OperationsHistory {

    protected ArrayList<BaseAccount> accounts = new ArrayList<BaseAccount>();

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

    public void openDebitAccountForPerson(Person owner)
    {
        Operation operation = new OpenDebitAccountOperation(this, owner);
        operation.execute();
        this.addOperation(operation);
    }

    public void addAccount(BaseAccount account)
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

    public ArrayList<BaseAccount> getAccounts() {
        return accounts;
    }
    public ArrayList<BaseAccount> getPersonAccounts(Person person) {
        ArrayList<BaseAccount> accounts_to_return = new ArrayList<BaseAccount>();
        for(BaseAccount acc : accounts){
            if(acc.getOwner() == person){
                accounts_to_return.add(acc);
            }
        }
        return accounts_to_return;
    }

    public Balance getBalance() {
        return balance;
    }
}
