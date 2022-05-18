import static org.junit.jupiter.api.Assertions.assertEquals;


import com.put.sdm.Bank;
import com.put.sdm.interbankpayments.InterbankPaymentAgency;
import com.put.sdm.interestrates.IInterestMechanism;
import com.put.sdm.operations.Operation;
import com.put.sdm.operations.bank.CloseDepositOperation;
import com.put.sdm.operations.bank.RepayAndCloseLoanOperation;
import com.put.sdm.operations.product.RepayLoanPartiallyOperation;
import com.put.sdm.operations.product.TransferMoneyOperation;
import com.put.sdm.products.DebitAccount;
import com.put.sdm.products.CreditAccount;
import com.put.sdm.products.Deposit;
import com.put.sdm.products.Loan;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BankingSystemTests {
    InterbankPaymentAgency interbank_payment_agency;

    Bank bank;
    Person person1;
    DebitAccount account1;
    CreditAccount credit_account1;

    Person person2;
    DebitAccount account2;

    @BeforeEach
    public void BankingSystemTestsSetup()
    {
        interbank_payment_agency = new InterbankPaymentAgency();

        bank = new Bank(interbank_payment_agency);
        interbank_payment_agency.addBank(bank);

        person1 = new Person("Adam", "Adamowski");
        person2 = new Person("Damian", "Damianowski");

        account1 = new DebitAccount(person1);
        bank.addAccount(account1);

        credit_account1 = new CreditAccount(new DebitAccount(person1));
        bank.addAccount(credit_account1);

        account2 = new DebitAccount(person2);
        bank.addAccount(account2);
    }


    @Test
    void nameTest() {
        assertEquals("Adam", person1.getFirstName());
    }

    @Test
    void surnameTest() {
        assertEquals("Adamowski", person1.getLastName());
    }

    @Test
    void fullNameTest() {
        assertEquals("Adam Adamowski", person1.getFullName());
    }

    @Test
    void accountBalanceTest()
    {
        assertEquals(0,account1.getBalance().getValue());

        account1.increaseBalance(new Balance(10));
        assertEquals(10,account1.getBalance().getValue());

        account1.decreaseBalance(new Balance(5));
        assertEquals(5,account1.getBalance().getValue());

        //Decreasing balance of account below 0 should not be possible
        account1.decreaseBalance(new Balance(10));
        assertEquals(5,account1.getBalance().getValue());
    }

    @Test
    void creditAccountTest()
    {
        assertEquals(0.f, credit_account1.getBalance().getValue());
        assertEquals(0.f, credit_account1.getCredit().getValue());

        credit_account1.increaseBalance(new Balance(50.f));
        assertEquals(50.f, credit_account1.getBalance().getValue());

        credit_account1.setCreditLimit(new Balance(-100.f));
        assertEquals(-100.f, credit_account1.getCreditLimit().getValue());

        {
            Operation operation = new TransferMoneyOperation(credit_account1, account2, new Balance(90.f));
            operation.execute();
            bank.addOperation(operation);
        }

        assertEquals(0.f, credit_account1.getBalance().getValue());
        assertEquals(-40.f, credit_account1.getCredit().getValue());


        //Decreasing balance of account below credit limit should not be possible
        {
            Operation operation = new TransferMoneyOperation(credit_account1, account2, new Balance(90.f));
            operation.execute();
            bank.addOperation(operation);
        }

        assertEquals(0.f, credit_account1.getBalance().getValue());
        assertEquals(-40.f, credit_account1.getCredit().getValue());
    }

    @Test
    void interBankTransactionsTest()
    {
        Bank bank2 = new Bank(interbank_payment_agency);
        interbank_payment_agency.addBank(bank2);

        Person person3  = new Person("Adrian", "Adrianowski");
        DebitAccount account3 = new DebitAccount(person3);

        bank2.addAccount(account3);

        account1.increaseBalance(new Balance(100.f));
        account3.increaseBalance(new Balance(100.f));

        assertEquals(100.f,account1.getBalance().getValue());
        assertEquals(100.f,account3.getBalance().getValue());

        if(interbank_payment_agency.isThereABankWithAccount(account3)){
            Bank target_bank = interbank_payment_agency.getBankWithAccount(account3);
            interbank_payment_agency.transferMoneyFromAccountToAccount(account1, bank, account3, target_bank, new Balance(50.f));
        }

        assertEquals(50.f,account1.getBalance().getValue());
        assertEquals(150.f,account3.getBalance().getValue());

    }

    @Test
    void depositTooEarlyTest()
    {
        account1.increaseBalance(new Balance(100.f));
        assertEquals(100.f,account1.getBalance().getValue());

        bank.openDepositForPersonUsingAccount(person1, account1, LocalDateTime.now().plusYears(1), new Balance(100.f));

        Deposit deposit = bank.getDeposits().get(0);
        assertEquals(100.f, deposit.getBalance().getValue());

        System.out.println(deposit.getBalance().getValue());

        {
            Operation operation = new CloseDepositOperation(bank, deposit);
            operation.execute();
            bank.addOperation(operation);
        }

        assertEquals(account1.getBalance().getValue(),100.f);

        System.out.println(account1.getBalance().getValue());
    }

    @Test
    void depositAtRightTimeTest()
    {
        account1.increaseBalance(new Balance(100.f));
        assertEquals(100.f,account1.getBalance().getValue());

        bank.openDepositForPersonUsingAccount(person1, account1, LocalDateTime.now().minusDays(1), new Balance(100.f));

        Deposit deposit = bank.getDeposits().get(0);
        assertEquals(100.f, deposit.getBalance().getValue());

        System.out.println(deposit.getBalance().getValue());

        IInterestMechanism interest = deposit.getInterestMechanism();

        Balance predicted_deposit_gain = new Balance(100.f * interest.calculateInterest(deposit));

        {
            Operation operation = new CloseDepositOperation(bank, deposit);
            operation.execute();
            bank.addOperation(operation);
        }

        assertEquals(account1.getBalance().getValue(),100.f + predicted_deposit_gain.getValue());

        System.out.println(account1.getBalance().getValue());
    }

    @Test
    void loanTest()
    {
        account1.increaseBalance(new Balance(100.f));
        assertEquals(100.f,account1.getBalance().getValue());

        bank.openLoanForPersonUsingAccount(person1, account1, new Balance(100.f));

        Loan loan = bank.getLoans().get(0);

        assertEquals(100.f, loan.getCredit().getValue());
        assertEquals(200.f, account1.getBalance().getValue());

        {
            Operation operation = new RepayLoanPartiallyOperation(loan, new Balance(50.f));
            operation.execute();
        }

        assertEquals(50.f, loan.getMoneyToRepay().getValue());
        assertEquals(150.f, account1.getBalance().getValue());

        loan.increaseCreditByInterest();
        assertEquals(50.f + 50.f * loan.getInterestMechanism().calculateInterest(loan), loan.getMoneyToRepay().getValue());

        Balance money_to_repay = loan.getMoneyToRepay();

        {
            Operation operation = new RepayAndCloseLoanOperation(bank, loan);
            operation.execute();
            bank.addOperation(operation);
        }

        assertEquals(200.f - loan.getCredit().getValue(), account1.getBalance().getValue());

        System.out.println(account1.getBalance().getValue());
    }

}