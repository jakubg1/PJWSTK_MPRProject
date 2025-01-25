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
            return new TransactionResult(TransactionStatus.DECLINED, "User already exists");
        }
        userStorage.addUser(userId, balance);
        return new TransactionResult(TransactionStatus.ACCEPTED, "User successfully registered");
    }

    public TransactionResult initiatePayment(String userId, int amount) {
        User user = userStorage.getUser(userId);
        if (user == null) {
            return new TransactionResult(TransactionStatus.DECLINED, "User does not exist");
        }
        if (user.getBalance() < amount) {
            return new TransactionResult(TransactionStatus.DECLINED, "User does not have enough money");
        }
        user.adjustBalance(-amount);
        return new TransactionResult(TransactionStatus.ACCEPTED, "Payment successful");
    }

    public TransactionResult depositMoney(String userId, int amount) {
        User user = userStorage.getUser(userId);
        if (user == null) {
            return new TransactionResult(TransactionStatus.DECLINED, "User does not exist");
        }
        user.adjustBalance(amount);
        return new TransactionResult(TransactionStatus.ACCEPTED, "Money deposited successfully");
    }

    public TransactionResult getUserData(String userId) {
        User user = userStorage.getUser(userId);
        if (user == null) {
            return new TransactionResult(TransactionStatus.DECLINED, "User does not exist");
        }
        return new TransactionResult(TransactionStatus.ACCEPTED, "Money = " + user.getBalance());
    }
}
