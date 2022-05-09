package com.put.sdm.interestrates;

import com.put.sdm.products.Product;

public class DepositInterestRateA implements IInterestMechanism {

    public float calculateInterest(Product deposit) {
        return 0.1f;
    }

}
