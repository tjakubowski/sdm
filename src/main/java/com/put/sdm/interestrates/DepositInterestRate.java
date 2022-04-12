package com.put.sdm.interestrates;

import com.put.sdm.products.Product;
import java.time.LocalDateTime;
import java.time.Duration;

public class DepositInterestRate implements IInterestMechanism {

    protected static final float INTEREST_RATE = 0.02f;
    protected static final int MINIMAL_DAY_COUNT = 365;

    public float calculateInterest(Product product) {
        Duration diff = Duration.between(LocalDateTime.now(), product.getOpeningDateTime());

        long daysDifference = diff.toDays();

        if (daysDifference > MINIMAL_DAY_COUNT) {
            return (float) (Math.floor((daysDifference - MINIMAL_DAY_COUNT) / 10f) * INTEREST_RATE);
        }

        return 0;
    }
}
