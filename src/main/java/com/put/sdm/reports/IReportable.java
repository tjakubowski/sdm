package com.put.sdm.reports;

import com.put.sdm.products.Deposit;
import com.put.sdm.products.Loan;
import com.put.sdm.products.Product;

public interface IReportable {
    String visitDeposit(Deposit deposit);

    String visitLoan(Loan loan);

    String visitProduct(Product product);
}
