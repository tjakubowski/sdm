package com.put.sdm.reports;

import com.put.sdm.products.Deposit;
import com.put.sdm.products.Loan;
import com.put.sdm.products.Product;

public class Report implements IReportable {
    public String visitDeposit(Deposit deposit) {
        return String.format("%s, closes: %s", this.visitProduct(deposit), deposit.getClosingDateTime());
    }

    public String visitProduct(Product product) {
        return String.format("%s %s: %s", product.getOwner().getFirstName(), product.getOwner().getLastName(), product.getBalance().getValue());
    }
}
