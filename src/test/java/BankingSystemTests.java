import static org.junit.jupiter.api.Assertions.assertEquals;


import com.put.sdm.Bank;
import com.put.sdm.operations.Operation;
import com.put.sdm.operations.bank.InterBankPaymentOperation;
import com.put.sdm.operations.product.TransferMoneyOperation;
import com.put.sdm.products.Account;
import com.put.sdm.products.DebitAccount;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankingSystemTests {
    Bank bank;
    Person person1;
    Account account1;
    DebitAccount debit_account1;

    Person person2;
    Account account2;

    @BeforeEach
    public void BankingSystemTestsSetup() {
        bank = new Bank();

        person1 = new Person("Adam", "Adamowski");
        person2 = new Person("Damian", "Damianowski");

        account1 = new Account(person1);
        bank.addAccount(account1);

        debit_account1 = new DebitAccount(person1);
        bank.addAccount(debit_account1);

        account2 = new Account(person2);
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
    void accountBalanceTest() {
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
    void debitAccountTest(){
        assertEquals(0.f,debit_account1.getBalance().getValue());
        assertEquals(0.f,debit_account1.getCredit().getValue());

        debit_account1.increaseBalance(new Balance(50.f));
        assertEquals(50.f,debit_account1.getBalance().getValue());

        debit_account1.setCreditLimit(new Balance(-100.f));
        assertEquals(-100.f,debit_account1.getCreditLimit().getValue());

        {
            Operation operation = new TransferMoneyOperation(debit_account1, account2, new Balance(90.f));
            operation.execute();
            bank.addOperation(operation);
        }

        assertEquals(0.f,debit_account1.getBalance().getValue());
        assertEquals(-40.f,debit_account1.getCredit().getValue());


        //Decreasing balance of account below credit limit should not be possible
        {
            Operation operation = new TransferMoneyOperation(debit_account1, account2, new Balance(90.f));
            operation.execute();
            bank.addOperation(operation);
        }

        assertEquals(0.f,debit_account1.getBalance().getValue());
        assertEquals(-40.f,debit_account1.getCredit().getValue());
    }

    @Test
    void interBankTransactionsTest(){
        Bank bank1 = new Bank();
        Bank bank2 = new Bank();

        bank1.increaseBalance(new Balance(1000.f));
        bank2.increaseBalance(new Balance(1000.f));

        assertEquals(1000.f,bank1.getBalance().getValue());
        assertEquals(1000.f,bank2.getBalance().getValue());

        {
            Operation operation = new InterBankPaymentOperation(bank1, bank2, new Balance(400.f));
            operation.execute();
            bank1.addOperation(operation);
            bank2.addOperation(operation);
        }

        assertEquals(600.f,bank1.getBalance().getValue());
        assertEquals(1400.f,bank2.getBalance().getValue());
    }

}