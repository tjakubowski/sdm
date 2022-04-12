package com.put.sdm.products;

import com.put.sdm.products.object.Balance;
import com.put.sdm.products.object.Person;
import com.put.sdm.reports.IReportable;

import java.time.LocalDateTime;

public interface IProduct {
    void decreaseBalance(Balance payment);

    void increaseBalance(Balance payment);

    Balance getBalance();

    Person getOwner();

    LocalDateTime getOpeningDateTime();

    void updateOpeningTime();

    String accept(IReportable visitor);

    void transferMoney(Product otherAccount, Balance payment);
}
