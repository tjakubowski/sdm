package com.put.sdm;

import com.put.sdm.card.Card;
import com.put.sdm.operations.Operation;
import com.put.sdm.operations.card.MakeCardPayment;
import com.put.sdm.products.DebitAccount;
import com.put.sdm.products.BaseAccount;
import com.put.sdm.products.CreditAccount;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Person person1 = new Person("Adam", "Adamowski");
        Person person2 = new Person("Konrad", "Krzewski");

        Bank bank = new Bank();

        bank.openDebitAccountForPerson(person1);
        bank.openCreditAccountForPerson(person1);
        bank.openDebitAccountForPerson(person2);

        ArrayList<BaseAccount> person1Accounts = bank.getPersonAccounts(person1);
        DebitAccount person1Account1 = (DebitAccount)person1Accounts.get(0);
        CreditAccount person1Account2 = (CreditAccount)person1Accounts.get(1);

        person1Account2.setCreditLimit(new Balance(-100.f));

        bank.openCardForAccount(person1Account2);
        ArrayList<Card> person1Account2Cards = bank.getAccountCards(person1Account2);

        Card person1Account2Card1 = person1Account2Cards.get(0);

        {
            Operation operation = new MakeCardPayment(person1Account2Card1, new Balance(-10.f));
            operation.execute();
            bank.addOperation(operation);
        }

        for(Operation x : bank.getOperationHistory()){
           System.out.println(x.getExecutionDateTime().toLocalDate() + " " + x.getExecutionDateTime().toLocalTime() + ": " + x.getDescription());
        }
    }
}
