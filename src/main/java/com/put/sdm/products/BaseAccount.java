package com.put.sdm.products;

import com.put.sdm.products.object.Person;

public abstract class BaseAccount extends Product {

    public BaseAccount(Person owner) {
        super(owner);
    }
}
