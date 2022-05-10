package com.put.sdm.operations.bank;

import com.put.sdm.Bank;
import com.put.sdm.interestrates.DepositInterestRateA;
import com.put.sdm.operations.product.TransferMoneyOperation;
import com.put.sdm.products.BaseAccount;
import com.put.sdm.products.Deposit;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;

import java.time.LocalDateTime;

public class CloseDepositOperation extends BankOperation {
    protected Deposit deposit;

    public CloseDepositOperation(Bank bank, Deposit deposit) {
        super(bank, "Close deposit");
        this.deposit = deposit;
    }

    public void execute() {
        super.execute();

        if(LocalDateTime.now().isAfter(deposit.getClosingDateTime()))
        {
            deposit.increaseBalanceByInterest();
        }

        TransferMoneyOperation transfer_money_from_deposit_to_account = new TransferMoneyOperation(this.deposit, this.deposit.getConnectedAccount(), new Balance(this.deposit.getBalance().getValue()));
        transfer_money_from_deposit_to_account.execute();
        this.deposit.addOperation(transfer_money_from_deposit_to_account);
        this.deposit.getConnectedAccount().addOperation(transfer_money_from_deposit_to_account);
        this.bank.removeDeposit(this.deposit);
    }
}
