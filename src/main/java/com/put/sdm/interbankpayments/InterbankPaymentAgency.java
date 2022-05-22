package com.put.sdm.interbankpayments;

import com.put.sdm.Bank;
import com.put.sdm.operations.product.TransferMoneyOperation;
import com.put.sdm.products.BaseAccount;
import com.put.sdm.products.object.Balance;

import java.util.ArrayList;

public class InterbankPaymentAgency {
    protected ArrayList<Bank> banks = new ArrayList<>();

    public InterbankPaymentAgency()
    {

    }

    public void addBank(Bank bank_to_add)
    {
        banks.add(bank_to_add);
    }

    public boolean isThereABankWithAccount(BaseAccount baseAccount)
    {
        for(Bank bnk : banks){
            if(bnk.getAccounts().contains(baseAccount)){
                return true;
            }
        }

        return false;
    }

    public Bank getBankWithAccount(BaseAccount baseAccount)
    {
        for(Bank bnk : banks){
            if(bnk.getAccounts().contains(baseAccount)){
                return bnk;
            }
        }

        return null;
    }

    public void transferMoneyFromAccountToAccount(BaseAccount source_account, Bank source_bank, BaseAccount target_account, Bank target_bank, Balance money)
    {
        if(banks.contains(source_bank) && source_bank.getAccounts().contains(source_account) && banks.contains(target_bank) && target_bank.getAccounts().contains(target_account)){
            TransferMoneyOperation transfer_money = new TransferMoneyOperation(source_account, target_account, money);
            transfer_money.execute();

            source_account.addOperation(transfer_money);
            target_account.addOperation(transfer_money);
        }
    }
}
