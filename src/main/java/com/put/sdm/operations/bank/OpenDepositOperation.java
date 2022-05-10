package com.put.sdm.operations.bank;

import com.put.sdm.Bank;
import com.put.sdm.interestrates.DepositInterestRateA;
import com.put.sdm.operations.product.TransferMoneyOperation;
import com.put.sdm.products.BaseAccount;
import com.put.sdm.products.Deposit;
import com.put.sdm.products.Loan;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;

import java.time.LocalDateTime;

public class OpenDepositOperation extends BankOperation {
    protected Person owner;
    protected BaseAccount account;
    protected LocalDateTime close_datetime;
    protected Balance money;

    public OpenDepositOperation(Bank bank, Person owner, BaseAccount account, LocalDateTime close_datetime, Balance money) {
        super(bank, "Open deposit");
        this.owner = owner;
        this.account = account;
        this.close_datetime = close_datetime;
        this.money = money;
    }

    public void execute() {
        super.execute();

        if(account.getBalance().getValue() >= money.getValue()){
            Deposit new_deposit = new Deposit(owner, account, close_datetime, new DepositInterestRateA());
            this.bank.addDeposit(new_deposit);
            new_deposit.updateOpeningTime();

            TransferMoneyOperation transfer_money_from_account_to_deposit = new TransferMoneyOperation(this.account, new_deposit, new Balance(this.money.getValue()));
            transfer_money_from_account_to_deposit.execute();
            this.account.addOperation(transfer_money_from_account_to_deposit);
            new_deposit.addOperation(transfer_money_from_account_to_deposit);
        }
    }
}
