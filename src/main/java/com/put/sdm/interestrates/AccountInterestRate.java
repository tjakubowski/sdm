package com.put.sdm.interestrates;

import com.put.sdm.products.Product;
import com.put.sdm.products.object.Balance;

public class AccountInterestRate implements IInterestMechanism {

    protected static final int MINIMAL_CASH = 10000;
    protected static final float WORSE_INTEREST_RATE = 0.005f;
    protected static final float BETTER_INTEREST_RATE = 0.02f;

    public float calculateInterest(Product product) {
        Balance compareBalance = new Balance(MINIMAL_CASH);

        return compareBalance.compareTo(product.getBalance()) < 0 ? BETTER_INTEREST_RATE : WORSE_INTEREST_RATE;
    }

}
