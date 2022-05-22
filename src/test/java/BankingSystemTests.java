import com.put.sdm.Bank;
import com.put.sdm.interbankpayments.InterbankPaymentAgency;
import com.put.sdm.interestrates.DepositInterestRateB;
import com.put.sdm.interestrates.IInterestMechanism;
import com.put.sdm.interestrates.LoanInterestRateB;
import com.put.sdm.operations.Operation;
import com.put.sdm.operations.bank.CloseDepositOperation;
import com.put.sdm.operations.bank.RepayAndCloseLoanOperation;
import com.put.sdm.operations.product.*;
import com.put.sdm.products.*;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;
import com.put.sdm.reports.AccountsWithMoneyReport;
import com.put.sdm.reports.CompleteReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BankingSystemTests {
    InterbankPaymentAgency interbank_payment_agency;

    Bank bank;
    Person person1;
    Account account1;
    CreditAccount credit_account1;

    Person person2;
    Account account2;

    @BeforeEach
    public void BankingSystemTestsSetup()
    {
        interbank_payment_agency = new InterbankPaymentAgency();

        bank = new Bank(interbank_payment_agency);
        interbank_payment_agency.addBank(bank);

        person1 = new Person("Adam", "Adamowski");
        person2 = new Person("Damian", "Damianowski");

        account1 = bank.openDebitAccountForPerson(person1);

        credit_account1 = bank.openCreditAccountForPerson(person1);

        account2 = bank.openDebitAccountForPerson(person2);
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
        }

        assertEquals(0.f, credit_account1.getBalance().getValue());
        assertEquals(-40.f, credit_account1.getCredit().getValue());


        //Decreasing balance of account below credit limit should not be possible
        {
            Operation operation = new TransferMoneyOperation(credit_account1, account2, new Balance(90.f));
            operation.execute();
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
        Account account3 = new Account(person3);

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

        Deposit deposit = bank.openDepositForPersonUsingAccount(person1, account1, LocalDateTime.now().plusYears(1), new Balance(100.f));

        {
            Operation operation = new ChangeDepositInterestRateMechanism(deposit, new DepositInterestRateB());
            operation.execute();
        }

        assertEquals(100.f, deposit.getBalance().getValue());

        System.out.println(deposit.getBalance().getValue());

        {
            Operation operation = new CloseDepositOperation(bank, deposit);
            operation.execute();
        }

        assertEquals(account1.getBalance().getValue(),100.f);

        System.out.println(account1.getBalance().getValue());
    }

    @Test
    void depositAtRightTimeTest()
    {
        account1.increaseBalance(new Balance(100.f));
        assertEquals(100.f,account1.getBalance().getValue());

        Deposit deposit = bank.openDepositForPersonUsingAccount(person1, account1, LocalDateTime.now().minusDays(1), new Balance(100.f));

        {
            Operation operation = new ChangeDepositInterestRateMechanism(deposit, new DepositInterestRateB());
            operation.execute();
        }

        assertEquals(100.f, deposit.getBalance().getValue());

        System.out.println(deposit.getBalance().getValue());

        IInterestMechanism interest = deposit.getInterestMechanism();

        Balance predicted_deposit_gain = new Balance(100.f * interest.calculateInterest(deposit));

        {
            Operation operation = new CloseDepositOperation(bank, deposit);
            operation.execute();
        }

        assertEquals(account1.getBalance().getValue(),100.f + predicted_deposit_gain.getValue());

        System.out.println(account1.getBalance().getValue());
    }

    @Test
    void loanTest()
    {
        account1.increaseBalance(new Balance(100.f));
        assertEquals(100.f,account1.getBalance().getValue());

        Loan loan = bank.openLoanForPersonUsingAccount(person1, account1, new Balance(100.f));

        {
            Operation operation = new ChangeLoanInterestRateMechanism(loan, new LoanInterestRateB());
            operation.execute();
        }

        assertEquals(100.f, loan.getCredit().getValue());
        assertEquals(200.f, account1.getBalance().getValue());

        {
            Operation operation = new RepayLoanPartiallyOperation(loan, new Balance(50.f));
            operation.execute();
        }

        assertEquals(50.f, loan.getMoneyToRepay().getValue());
        assertEquals(150.f, account1.getBalance().getValue());

        {
            Operation operation = new IncreaseCreditByInterest(loan);
            operation.execute();
        }

        assertEquals(50.f + 50.f * loan.getInterestMechanism().calculateInterest(loan), loan.getMoneyToRepay().getValue());

        Balance money_to_repay = loan.getMoneyToRepay();

        {
            Operation operation = new RepayAndCloseLoanOperation(bank, loan);
            operation.execute();
        }

        assertEquals(200.f - loan.getCredit().getValue(), account1.getBalance().getValue());

        System.out.println(account1.getBalance().getValue());
    }

    @Test
    void reportTest(){
        Account account3 = bank.openDebitAccountForPerson(person1);
        account3.increaseBalance(new Balance(100.f));

        Account account4 = bank.openDebitAccountForPerson(person2);
        Account account5 = bank.openDebitAccountForPerson(person1);

        {
            Operation operation = new TransferMoneyOperation(account3, account4, new Balance(50.f));
            operation.execute();
        }

        {
            Operation operation = new TransferMoneyOperation(account4, account1, new Balance(25.f));
            operation.execute();
        }

        ArrayList<Product> products = bank.prepareReportProducts(new AccountsWithMoneyReport());

        assertTrue(products.contains(account1));
        assertFalse(products.contains(credit_account1));
        assertFalse(products.contains(account2));
        assertTrue(products.contains(account3));
        assertTrue(products.contains(account4));
        assertFalse(products.contains(account5));

        ArrayList<Product> products2 = bank.prepareReportProducts(new CompleteReport());

        assertTrue(products2.contains(account1));
        assertTrue(products2.contains(credit_account1));
        assertTrue(products2.contains(account2));
        assertTrue(products2.contains(account3));
        assertTrue(products2.contains(account4));
        assertTrue(products2.contains(account5));

        System.out.println("Accounts with money history report:");
        System.out.println(bank.prepareAccountsWithMoneyReport());

        System.out.println("Complete history report:");
        System.out.println(bank.prepareCompleteReport());
    }

}