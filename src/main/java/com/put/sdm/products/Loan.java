package com.put.sdm.products;

import com.put.sdm.interestrates.LoanInterestRate;
import com.put.sdm.operations.Operation;
import com.put.sdm.operations.product.OpenCreditOperation;
import com.put.sdm.operations.product.RepayCreditOperation;
import com.put.sdm.products.object.Balance;
import com.put.sdm.reports.IReportable;

import java.time.LocalDateTime;

public class Loan extends Product {

    protected Account consumerAccount;

    protected LoanInterestRate interestRate;

    protected LocalDateTime closingDateTime;

    protected LocalDateTime nextRepaymentDateTime;

    public Loan(Account consumerAccount) {
        super(consumerAccount.getOwner());

        this.consumerAccount = consumerAccount;
    }

    public Loan(Account consumerAccount, LoanInterestRate interestRate) {
        super(consumerAccount.getOwner());

        this.consumerAccount = consumerAccount;
        this.interestRate = interestRate;
    }

    public void openCredit(Balance credit)
    {
        Operation operation = new OpenCreditOperation(this.consumerAccount, this, credit);
        operation.execute();

        this.addOperation(operation);
    }

    public void repayCredit(Balance payment)
    {
        Operation operation = new RepayCreditOperation(this.consumerAccount, this, payment);
        operation.execute();

        this.addOperation(operation);
    }

    public void decreaseBalance(Balance payment)
    {
        this.getBalance().decrease(payment);
    }

    public void increaseBalance(Balance payment)
    {
        this.getBalance().increase(payment);
    }

    public LocalDateTime getClosingDateTime()
    {
        return this.closingDateTime;
    }

    public LocalDateTime getNextRepaymentDateTime()
    {
        return this.nextRepaymentDateTime;
    }

    public void updateNextRepaymentDateTime()
    {
        this.nextRepaymentDateTime = this.nextRepaymentDateTime.plusYears(1);
    }

    public void updateOpeningTime()
    {
        super.updateOpeningTime();

        this.closingDateTime = this.openingDateTime.plusYears(1);
        this.nextRepaymentDateTime = this.closingDateTime.plusYears(1);
    }

    public String accept(IReportable visitor)
    {
        return visitor.visitProduct(this);
    }
}
