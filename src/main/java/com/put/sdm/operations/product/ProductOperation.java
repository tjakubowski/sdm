package com.put.sdm.operations.product;

import com.put.sdm.operations.Operation;
import com.put.sdm.products.Product;

public class ProductOperation extends Operation {

    protected Product product;

    public ProductOperation(Product product, String description) {
        super(description);
        this.product = product;
    }
}
