package com.springbank.entity;


import jakarta.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    private String userId;

    private double balance;

    public Account(String userId, double balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return Math.round(balance * 100.0) / 100.0;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return accountId + "       " + String.format("%.2f ",balance) ;
    }
    public Account() {}
}
