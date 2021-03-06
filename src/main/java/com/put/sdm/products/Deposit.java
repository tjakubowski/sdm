package com.put.sdm.products;

import com.put.sdm.interestrates.IInterestMechanism;
import com.put.sdm.operations.Operation;
import com.put.sdm.operations.product.TransferMoneyOperation;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;
import com.put.sdm.reports.IElement;
import com.put.sdm.reports.IVisitor;

import java.time.LocalDateTime;

public class Deposit extends Product implements IElement {

    BaseAccount connectedAccount;

    protected IInterestMechanism interestRate;

    protected LocalDateTime closingDateTime;

    public Deposit(Person owner, BaseAccount account, LocalDateTime closingDateTime, IInterestMechanism interestRate)
    {
        super(owner);

        this.connectedAccount = account;
        this.interestRate = interestRate;
        this.closingDateTime = closingDateTime;
    }

    public BaseAccount getConnectedAccount()
    {
        return connectedAccount;
    }

    public void setInterestMechanism(IInterestMechanism interestRate)
    {
        this.interestRate = interestRate;
    }
    public IInterestMechanism getInterestMechanism()
    {
        return interestRate;
    }

    public void increaseBalanceByInterest()
    {
        this.balance.increase(new Balance(this.balance.getValue() * interestRate.calculateInterest(this)));
    }

    public LocalDateTime getClosingDateTime() {
        return closingDateTime;
    }

    public void updateOpeningTime() {
        super.updateOpeningTime();
    }

    public Product accept(IVisitor visitor) {
        return visitor.visit(this);
    }
}
