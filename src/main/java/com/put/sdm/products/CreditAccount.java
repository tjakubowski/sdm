package com.put.sdm.products;

import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;
import com.put.sdm.reports.IElement;
import com.put.sdm.reports.IVisitor;

import java.time.LocalDateTime;

public class CreditAccount extends BaseAccount implements IElement {
    private BaseAccount base_account;

    protected Balance credit_limit = new Balance(0.f);

    protected Balance credit = new Balance(0.f);

    public CreditAccount(BaseAccount base_account) {
        super(new Person("", ""));
        this.base_account = base_account;
    }

    public boolean decreaseBalance(Balance payment) {
        if(payment.getValue() > base_account.getBalance().getValue()) {
            payment.decrease(base_account.getBalance());
            Balance new_credit_value = new Balance(this.credit.getValue());
            new_credit_value.decrease(payment);
            if(new_credit_value.getValue() >= this.credit_limit.getValue()) {
                base_account.decreaseBalance(base_account.getBalance());
                this.credit = new_credit_value;
                return true;
            }else{
                System.out.println("Payment greater than credit limit");
                return false;
            }
        }else {
            base_account.decreaseBalance(payment);
            return true;
        }
    }

    public void increaseBalance(Balance payment) {
        base_account.increaseBalance(payment);
    }

    public Balance getBalance() {
        return base_account.getBalance();
    }

    public Person getOwner() {
        return base_account.getOwner();
    }

    public LocalDateTime getOpeningDateTime() {
        return base_account.getOpeningDateTime();
    }

    public void updateOpeningTime() {
        base_account.updateOpeningTime();
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

    public Product accept(IVisitor visitor) {
        return visitor.visit(this);
    }
}
