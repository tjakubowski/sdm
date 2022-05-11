package com.put.sdm.products;

import com.put.sdm.interestrates.IInterestMechanism;
import com.put.sdm.operations.Operation;
import com.put.sdm.operations.product.TransferMoneyOperation;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;
import com.put.sdm.reports.IReportable;

import java.time.LocalDateTime;

public class Loan extends Product {

    BaseAccount connectedAccount;

    protected IInterestMechanism interestRate;

    Balance credit;

    public Loan(Person owner, BaseAccount account, Balance credit, IInterestMechanism interestRate)
    {
        super(owner);

        this.connectedAccount = account;
        this.interestRate = interestRate;
        this.credit = credit;
    }

    public void increaseCreditByInterest()
    {
        this.credit.increase(new Balance(this.getMoneyToRepay().getValue() * interestRate.calculateInterest(this)));
    }

    public BaseAccount getConnectedAccount() {
        return connectedAccount;
    }

    public IInterestMechanism getInterestMechanism()
    {
        return interestRate;
    }

    public Balance getCredit()
    {
        return credit;
    }

    public Balance getMoneyToRepay()
    {
        return new Balance(credit.getValue() - balance.getValue());
    }

    public String accept(IReportable visitor)
    {
        return visitor.visitProduct(this);
    }
}
