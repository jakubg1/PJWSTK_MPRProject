package com.pjatk.s32362Bank;

import org.springframework.stereotype.Component;

@Component
public class Bank {
    private final UserStorage userStorage;

    public Bank(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public TransactionResult registerUser(String userId, int balance) {
        if (userStorage.getUser(userId) != null) {
            return new TransactionResult(TransactionStatus.DECLINED, "User " + userId + " already exists");
        }
        if (balance < 0) {
            return new TransactionResult(TransactionStatus.DECLINED, "User cannot have negative balance (got: " + balance + ")");
        }
        userStorage.addUser(userId, balance);
        return new TransactionResult(TransactionStatus.ACCEPTED, "User " + userId + " successfully registered");
    }

    public TransactionResult initiatePayment(String userId, int amount) {
        User user = userStorage.getUser(userId);
        if (user == null) {
            return new TransactionResult(TransactionStatus.DECLINED, "User " + userId + " does not exist");
        }
        if (amount <= 0) {
            return new TransactionResult(TransactionStatus.DECLINED, "User " + userId + " cannot pay zero or negative amount (got: " + amount + ")");
        }
        if (user.getBalance() < amount) {
            return new TransactionResult(TransactionStatus.DECLINED, "User " + userId + " does not have enough funds (got: " + user.getBalance() + " while wanted to pay " + amount + ")");
        }
        user.adjustBalance(-amount);
        return new TransactionResult(TransactionStatus.ACCEPTED, "User " + userId + ": Payment successful");
    }

    public TransactionResult depositMoney(String userId, int amount) {
        User user = userStorage.getUser(userId);
        if (user == null) {
            return new TransactionResult(TransactionStatus.DECLINED, "User " + userId + " does not exist");
        }
        if (amount <= 0) {
            return new TransactionResult(TransactionStatus.DECLINED, "User " + userId + " cannot deposit zero or negative amount (got: " + amount + ")");
        }
        user.adjustBalance(amount);
        return new TransactionResult(TransactionStatus.ACCEPTED, "User " + userId + ": Money deposited successfully");
    }

    public TransactionResult getUserData(String userId) {
        User user = userStorage.getUser(userId);
        if (user == null) {
            return new TransactionResult(TransactionStatus.DECLINED, "User " + userId + " does not exist");
        }
        return new TransactionResult(TransactionStatus.ACCEPTED, "User " + userId + ": Balance = " + user.getBalance());
    }
}
