package com.expensesplitter.model;

import java.io.Serializable;

public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String description;
    private final double amount;
    private final Person payer;

    public Expense(String description, double amount, Person payer) {
        this.description = description;
        this.amount = amount;
        this.payer = payer;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public Person getPayer() { return payer; }
    public Person getPaidBy() { return payer; } // compatibility

    @Override
    public String toString() {
        return description + " - Rs. " + String.format("%.2f", amount) + " (Paid by " + payer.getName() + ")";
    }
}
