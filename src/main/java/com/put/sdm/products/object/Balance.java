package com.put.sdm.products.object;

public class Balance {

    protected float value;

    public Balance() {
        this.value = 0;
    }

    public Balance(float value) {
        this.value = value;
    }

    public float compareTo(Balance otherBalance) {
        return otherBalance.getValue() - this.value;
    }

    public void increase(Balance payment)
    {
        this.value += payment.getValue();
    }

    public void decrease(Balance payment)
    {
        this.value -= payment.getValue();
    }

    public float getValue()
    {
        return this.value;
    }
}
