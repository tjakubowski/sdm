package com.put.sdm;

import com.put.sdm.card.Card;
import com.put.sdm.operations.Operation;
import com.put.sdm.operations.bank.*;
import com.put.sdm.operationshistory.OperationsHistory;
import com.put.sdm.products.BaseAccount;
import com.put.sdm.products.Deposit;
import com.put.sdm.products.Loan;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bank extends OperationsHistory {

    protected ArrayList<BaseAccount> accounts = new ArrayList<BaseAccount>();

    protected ArrayList<Card> cards = new ArrayList<Card>();

    protected ArrayList<Deposit> deposits = new ArrayList<Deposit>();
    protected ArrayList<Loan> loans = new ArrayList<Loan>();

    protected Balance balance;

    public Bank() {
        super("Open bank");

        this.balance = new Balance();
    }

    public void openDebitAccountForPerson(Person owner)
    {
        Operation operation = new OpenDebitAccountOperation(this, owner);
        operation.execute();
        this.addOperation(operation);
    }

    public void openCreditAccountForPerson(Person owner)
    {
        Operation operation = new OpenCreditAccountOperation(this, owner);
        operation.execute();
        this.addOperation(operation);
    }

    public void openCardForAccount(BaseAccount account)
    {
        Operation operation = new OpenCardOperation(this, account);
        operation.execute();
        this.addOperation(operation);
    }

    public void openDepositForPersonUsingAccount(Person owner, BaseAccount account, LocalDateTime close_datetime, Balance money)
    {
        Operation operation = new OpenDepositOperation(this, owner, account, close_datetime, money);
        operation.execute();
        this.addOperation(operation);
    }

    public void openLoanForPersonUsingAccount(Person owner, BaseAccount account, Balance money)
    {
        Operation operation = new OpenLoanOperation(this, owner, account, money);
        operation.execute();
        this.addOperation(operation);
    }

    public void addAccount(BaseAccount account)
    {
        this.accounts.add(account);
    }

    public void addCard(Card card)
    {
        this.cards.add(card);
    }

    public void addDeposit(Deposit deposit)
    {
        this.deposits.add(deposit);
    }
    public void removeDeposit(Deposit deposit)
    {
        this.deposits.remove(deposit);
    }

    public void addLoan(Loan loan)
    {
        this.loans.add(loan);
    }
    public void removeLoan(Loan loan)
    {
        this.loans.remove(loan);
    }

    public void increaseBalance(Balance payment)
    {
        this.balance.increase(payment);
    }

    public void decreaseBalance(Balance payment)
    {
        this.balance.decrease(payment);
    }

    public ArrayList<BaseAccount> getAccounts() {
        return accounts;
    }
    public ArrayList<Card> getCards() {
        return cards;
    }
    public ArrayList<Loan> getLoans() {
        return loans;
    }
    public ArrayList<Deposit> getDeposits() {
        return deposits;
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
    public ArrayList<Card> getAccountCards(BaseAccount account) {
        ArrayList<Card> cards_to_return = new ArrayList<Card>();
        for(Card card : cards){
            if(card.getConnectedAccount() == account){
                cards_to_return.add(card);
            }
        }
        return cards_to_return;
    }

    public Balance getBalance() {
        return balance;
    }
}
