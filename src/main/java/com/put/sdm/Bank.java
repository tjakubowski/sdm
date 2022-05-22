package com.put.sdm;

import com.put.sdm.card.Card;
import com.put.sdm.interbankpayments.InterbankPaymentAgency;
import com.put.sdm.operations.Operation;
import com.put.sdm.operations.bank.*;
import com.put.sdm.operationshistory.OperationsHistory;
import com.put.sdm.products.*;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;
import com.put.sdm.reports.AccountsWithMoneyReport;
import com.put.sdm.reports.CompleteReport;
import com.put.sdm.reports.IVisitor;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bank extends OperationsHistory {

    protected ArrayList<Product> all_products = new ArrayList<Product>();

    protected ArrayList<BaseAccount> accounts = new ArrayList<BaseAccount>();
    protected ArrayList<Deposit> deposits = new ArrayList<Deposit>();
    protected ArrayList<Loan> loans = new ArrayList<Loan>();

    protected ArrayList<Card> cards = new ArrayList<Card>();

    InterbankPaymentAgency interbank_payment_agency;

    public Bank(InterbankPaymentAgency interbank_payment_agency) {
        super("Open bank");

        this.interbank_payment_agency = interbank_payment_agency;
    }

    public Account openDebitAccountForPerson(Person owner)
    {
        Operation operation = new OpenDebitAccountOperation(this, owner);
        operation.execute();

        return (Account)accounts.get(accounts.size() - 1);
    }

    public CreditAccount openCreditAccountForPerson(Person owner)
    {
        Operation operation = new OpenCreditAccountOperation(this, owner);
        operation.execute();

        return (CreditAccount) accounts.get(accounts.size() - 1);
    }

    public Card openCardForAccount(BaseAccount account)
    {
        Operation operation = new OpenCardOperation(this, account);
        operation.execute();

        return cards.get(cards.size() - 1);
    }

    public Deposit openDepositForPersonUsingAccount(Person owner, BaseAccount account, LocalDateTime close_datetime, Balance money)
    {
        Operation operation = new OpenDepositOperation(this, owner, account, close_datetime, money);
        operation.execute();

        return deposits.get(deposits.size() - 1);
    }

    public Loan openLoanForPersonUsingAccount(Person owner, BaseAccount account, Balance money)
    {
        Operation operation = new OpenLoanOperation(this, owner, account, money);
        operation.execute();

        return loans.get(loans.size() - 1);
    }

    public void addAccount(BaseAccount account)
    {
        this.all_products.add(account);
        this.accounts.add(account);
    }

    public void addDeposit(Deposit deposit)
    {
        this.all_products.add(deposit);
        this.deposits.add(deposit);
    }
    public void removeDeposit(Deposit deposit)
    {
        this.all_products.remove(deposit);
        this.deposits.remove(deposit);
    }

    public void addLoan(Loan loan)
    {
        this.all_products.add(loan);
        this.loans.add(loan);
    }
    public void removeLoan(Loan loan)
    {
        this.all_products.remove(loan);
        this.loans.remove(loan);
    }

    public void addCard(Card card)
    {
        this.cards.add(card);
    }


    public ArrayList<BaseAccount> getAccounts() {
        return accounts;
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

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Product> prepareReportProducts(IVisitor report) {
        ArrayList<Product> to_return = new ArrayList<Product>();

        for(Product prod : all_products) {
            to_return.add(prod.accept(report));
        }

        return to_return;
    }

    public String prepareCompleteReport() {
        IVisitor history_report = new CompleteReport();

        ArrayList<Product> products = prepareReportProducts(history_report);

        StringBuilder to_return = new StringBuilder();

        for(Product prod : products){
            if(prod == null) { continue; }
            for(Operation op : prod.getOperationHistory()){
                to_return.append(op.getExecutionDateTime().toString()).append(" ").append(prod.getId()).append(": ").append(op.getDescription()).append("\n");
            }
        }

        return to_return.toString();
    }

    public String prepareAccountsWithMoneyReport() {
        IVisitor history_report = new AccountsWithMoneyReport();

        ArrayList<Product> products = prepareReportProducts(history_report);

        StringBuilder to_return = new StringBuilder();

        for(Product prod : products){
            if(prod == null) { continue; }
            for(Operation op : prod.getOperationHistory()){
                to_return.append(op.getExecutionDateTime().toString()).append(" ").append(prod.getId()).append(": ").append(op.getDescription()).append("\n");
            }
        }

        return to_return.toString();
    }
}
