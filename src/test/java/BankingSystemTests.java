import static org.junit.jupiter.api.Assertions.assertEquals;


import com.put.sdm.Bank;
import com.put.sdm.products.Account;
import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;
import org.junit.jupiter.api.Test;

class BankingSystemTests {

    private final Bank bank = new Bank();
    Person person1 = new Person("Adam", "Adamowski");
    Account account= new Account(person1);
    @Test
    void NameTest() {
        assertEquals("Adam",person1.getFirstName());
    }
    @Test
    void surNameTest() {
        assertEquals("Adamowski",person1.getLastName());
    }
    @Test
    void fullNameTest() {
        assertEquals("Adam Adamowski",person1.getFullName());
    }
    @Test
    void accountBalanceTest() {
        assertEquals(0,account.getBalance().getValue());
        account.increaseBalance(new Balance(10));
        assertEquals(10,account.getBalance().getValue());
        account.decreaseBalance(new Balance(5));
        assertEquals(5,account.getBalance().getValue());
        account.decreaseBalance(new Balance(10));
        assertEquals(-5,account.getBalance().getValue());

    }

}