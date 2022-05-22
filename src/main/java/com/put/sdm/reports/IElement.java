package com.put.sdm.reports;

import com.put.sdm.products.Product;

public interface IElement {
    Product accept(IVisitor visitor);
}
