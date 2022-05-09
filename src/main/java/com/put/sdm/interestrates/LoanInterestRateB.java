package com.put.sdm.interestrates;

import com.put.sdm.products.Loan;
import com.put.sdm.products.Product;

public class LoanInterestRateB implements IInterestMechanism {

    public float calculateInterest(Product loan) {
        return 0.2f;
    }

}