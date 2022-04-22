package com.put.sdm.products;

import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;

public class DebitAccount extends Account {
    protected Balance credit_limit;

    protected Balance credit;

    public void decreaseBalance(Balance payment) {
        if(payment.getValue() > this.balance.getValue()) {
            payment.credit(this.balance);
            Balance new_credit_value = credit;
            new_credit_value.credit(payment);
            if(new_credit_value.getValue() <= this.credit_limit.getValue()) {
                this.balance = new Balance(0.f);
                this.credit = new_credit_value;
            }else{
                System.out.println("Payment greater than credit limit");
            }
        }else {
            this.balance.credit(payment);
        }
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

    public DebitAccount(Person owner) {
        super(owner);
    }
}
