package com.pjatk.s32362Bank;

public class User {
    private final String id;
    private int balance;

    public User(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int amount) {
        this.balance = amount;
    }

    public void adjustBalance(int amount) {
        this.balance += amount;
    }
}
