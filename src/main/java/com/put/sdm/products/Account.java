package com.put.sdm.products;

import com.put.sdm.products.object.Person;
import com.put.sdm.reports.IElement;
import com.put.sdm.reports.IVisitor;

public class Account extends BaseAccount implements IElement {

    public Account(Person owner) {
        super(owner);
    }

    public Product accept(IVisitor visitor) {
        return visitor.visit(this);
    }
}
