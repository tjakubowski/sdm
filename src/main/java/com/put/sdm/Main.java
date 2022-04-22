package com.put.sdm;

import com.put.sdm.operations.Operation;
import com.put.sdm.products.Account;
import com.put.sdm.products.BaseAccount;
import com.put.sdm.products.DebitAccount;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Person person1 = new Person("Adam", "Adamowski");
        Person person2 = new Person("Konrad", "Krzewski");

        Bank bank = new Bank();

        bank.openAccountForPerson(person1);
        bank.openDebitAccountForPerson(person1);
        bank.openAccountForPerson(person2);

        ArrayList<BaseAccount> person1Accounts = bank.getPersonAccounts(person1);
        Account person1Account1 = (Account)person1Accounts.get(0);
        DebitAccount person1Account2 = (DebitAccount)person1Accounts.get(1);


        for(Operation x : bank.getOperationHistory()){
           System.out.println(x.getExecutionDateTime().toLocalDate() + " " + x.getExecutionDateTime().toLocalTime() + ": " + x.getDescription());
        }
    }
}
