package com.put.sdm.products;

import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;

import java.time.LocalDateTime;

public interface IProduct {
    boolean decreaseBalance(Balance payment);

    void increaseBalance(Balance payment);

    Balance getBalance();

    Person getOwner();

    LocalDateTime getOpeningDateTime();

    void updateOpeningTime();
}
