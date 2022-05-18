package com.put.sdm.products;

import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;
import com.put.sdm.reports.IReportable;

import java.time.LocalDateTime;

public class CreditAccount extends BaseAccount {
    private DebitAccount debit_account;

    protected Balance credit_limit = new Balance(0.f);

    protected Balance credit = new Balance(0.f);

    public void decreaseBalance(Balance payment) {
        if(payment.getValue() > debit_account.getBalance().getValue()) {
            payment.decrease(debit_account.getBalance());
            Balance new_credit_value = new Balance(this.credit.getValue());
            new_credit_value.decrease(payment);
            if(new_credit_value.getValue() >= this.credit_limit.getValue()) {
                debit_account.decreaseBalance(debit_account.getBalance());
                this.credit = new_credit_value;
            }else{
                System.out.println("Payment greater than credit limit");
            }
        }else {
            debit_account.decreaseBalance(payment);
        }
    }

    public void increaseBalance(Balance payment) {
        debit_account.increaseBalance(payment);
    }

    public Balance getBalance() {
        return debit_account.getBalance();
    }

    public Person getOwner() {
        return debit_account.getOwner();
    }

    public LocalDateTime getOpeningDateTime() {
        return debit_account.getOpeningDateTime();
    }

    public void updateOpeningTime() {
        debit_account.updateOpeningTime();
    }

    public String accept(IReportable visitor) {
        return debit_account.accept(visitor);
    }

    public Balance getCredit() {
        return credit;
    }

    public Balance getCreditLimit(){
        return credit_limit;
    }

    public void setCreditLimit(Balance new_credit_limit){
        credit_limit = new_credit_limit;
    }

    public CreditAccount(DebitAccount debit_account) {
        super(new Person("", ""));
        this.debit_account = debit_account;
    }
}
