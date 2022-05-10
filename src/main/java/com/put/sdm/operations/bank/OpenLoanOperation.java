package com.put.sdm.operations.bank;

import com.put.sdm.Bank;
import com.put.sdm.interestrates.DepositInterestRateA;
import com.put.sdm.interestrates.LoanInterestRateA;
import com.put.sdm.operations.product.TransferMoneyOperation;
import com.put.sdm.products.BaseAccount;
import com.put.sdm.products.CreditAccount;
import com.put.sdm.products.Deposit;
import com.put.sdm.products.Loan;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;

import java.time.LocalDateTime;

public class OpenLoanOperation extends BankOperation {
    protected Person owner;
    protected BaseAccount account;
    protected Balance money;

    public OpenLoanOperation(Bank bank, Person owner, BaseAccount account, Balance money) {
        super(bank, "Open loan");
        this.owner = owner;
        this.account = account;
        this.money = money;
    }

    public void execute() {
        super.execute();

        Loan new_loan = new Loan(owner, account, money, new DepositInterestRateA());
        new_loan.increaseBalance(money);
        this.bank.addLoan(new_loan);
        new_loan.updateOpeningTime();

        TransferMoneyOperation transfer_money_from_loan_to_account = new TransferMoneyOperation(new_loan, this.account, this.money);
        transfer_money_from_loan_to_account.execute();
        this.account.addOperation(transfer_money_from_loan_to_account);
        new_loan.addOperation(transfer_money_from_loan_to_account);
    }
}
