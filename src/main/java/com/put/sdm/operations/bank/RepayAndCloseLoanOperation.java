package com.put.sdm.operations.bank;

import com.put.sdm.Bank;
import com.put.sdm.operations.product.TransferMoneyOperation;
import com.put.sdm.products.Loan;
import com.put.sdm.products.object.Balance;

public class RepayAndCloseLoanOperation extends BankOperation {
    Loan loan;

    public RepayAndCloseLoanOperation(Bank bank, Loan loan) {
        super(bank, "Repay and close loan");

        this.loan = loan;
    }

    public void execute() {
        super.execute();

        if(loan.getConnectedAccount().getBalance().getValue() >= loan.getMoneyToRepay().getValue())
        {
            TransferMoneyOperation transfer_money_from_account_to_loan = new TransferMoneyOperation(loan.getConnectedAccount(), loan, new Balance(loan.getMoneyToRepay().getValue()));
            transfer_money_from_account_to_loan.execute();
            loan.addOperation(transfer_money_from_account_to_loan);
            loan.getConnectedAccount().addOperation(transfer_money_from_account_to_loan);
            this.bank.removeLoan(loan);
        }
    }
}
