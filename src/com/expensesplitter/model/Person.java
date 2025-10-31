package com.expensesplitter.model;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private double balance = 0.0;

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() { return name; }
    public double getBalance() { return balance; }
    public void setBalance(double b) { this.balance = b; }
    public void addBalance(double d) { this.balance += d; }
    @Override public String toString() { return name; }
}
