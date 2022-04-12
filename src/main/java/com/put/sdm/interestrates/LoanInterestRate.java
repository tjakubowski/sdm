package com.put.sdm.interestrates;

import com.put.sdm.products.Product;

import java.time.LocalDateTime;

public class LoanInterestRate implements IInterestMechanism {

    protected static final float STANDARD_INTEREST_RATE = 0.005f;

    protected float currentRate = 0;

    public float calculateInterest(Product product) {
        if(LocalDateTime.now().isAfter(product.getOpeningDateTime())) {
            this.currentRate += STANDARD_INTEREST_RATE;
        }

        return this.currentRate;
    }

}
