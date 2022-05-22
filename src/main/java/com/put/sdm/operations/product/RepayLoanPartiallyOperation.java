package com.put.sdm.operations.product;

import com.put.sdm.Bank;
import com.put.sdm.products.Deposit;
import com.put.sdm.products.Loan;
import com.put.sdm.products.object.Balance;

import java.time.LocalDateTime;

public class RepayLoanPartiallyOperation extends ProductOperation {
    Balance money;

    public RepayLoanPartiallyOperation(Loan loan, Balance money) {
        super(loan, "Partially repay loan");

        this.money = money;
    }

    public void execute() {
        super.execute();

        Loan loan = (Loan) this.product;

        if(loan.getConnectedAccount().getBalance().getValue() >= money.getValue() && loan.getMoneyToRepay().getValue() >= money.getValue())
        {
            TransferMoneyOperation transfer_money_from_account_to_loan = new TransferMoneyOperation(loan.getConnectedAccount(), loan, new Balance(this.money.getValue()));
            transfer_money_from_account_to_loan.execute();

            loan.addOperation(transfer_money_from_account_to_loan);
            loan.getConnectedAccount().addOperation(transfer_money_from_account_to_loan);
        }
    }
}
