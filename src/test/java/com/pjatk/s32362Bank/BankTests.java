package com.pjatk.s32362Bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BankTests {
    private Bank bank;

    @BeforeEach
    void prepareBank() {
        UserStorage userStorage = new UserStorage();
        bank = new Bank(userStorage);
        bank.registerUser("A", 1000);
    }

    @Test
    void registerUserSuccessfully() {
        TransactionResult result = bank.registerUser("B", 2000);
        assertThat(result.isAccepted()).isTrue();
    }

    @Test
    void registerUserSuccessfullyWithZeroFunds() {
        TransactionResult result = bank.registerUser("B", 0);
        assertThat(result.isAccepted()).isTrue();
    }

    @Test
    void registerUserFailureBecauseUserExists() {
        TransactionResult result = bank.registerUser("A", 400);
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void registerUserFailureBecauseNegativeFunds() {
        TransactionResult result = bank.registerUser("X", -100);
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void initiatePaymentSuccessfully() {
        TransactionResult result = bank.initiatePayment("A", 300);
        assertThat(result.isAccepted()).isTrue();
    }

    @Test
    void initiatePaymentFailureBecauseUserDoesNotExist() {
        TransactionResult result = bank.initiatePayment("X", 300);
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void initiatePaymentFailureBecauseNotEnoughFunds() {
        TransactionResult result = bank.initiatePayment("A", 30000);
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void initiatePaymentFailureBecauseNegativeAmount() {
        TransactionResult result = bank.initiatePayment("A", -300);
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void initiatePaymentFailureBecauseZeroAmount() {
        TransactionResult result = bank.initiatePayment("A", 0);
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void depositMoneySuccessfully() {
        TransactionResult result = bank.depositMoney("A", 2000);
        assertThat(result.isAccepted()).isTrue();
    }

    @Test
    void depositMoneyFailureBecauseUserDoesNotExist() {
        TransactionResult result = bank.depositMoney("X", 2000);
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void depositMoneyFailureBecauseNegativeAmount() {
        TransactionResult result = bank.depositMoney("A", -2000);
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void depositMoneyFailureBecauseZeroAmount() {
        TransactionResult result = bank.depositMoney("A", 0);
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void getUserDataSuccessfully() {
        TransactionResult result = bank.getUserData("A");
        assertThat(result.isAccepted()).isTrue();
    }

    @Test
    void getUserDataFailureBecauseUserDoesNotExist() {
        TransactionResult result = bank.getUserData("X");
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void getUserDataCheckResult() {
        TransactionResult result = bank.getUserData("A");
        assertThat(result.getMessage()).isEqualTo("User A: Balance = 1000");
    }
}
