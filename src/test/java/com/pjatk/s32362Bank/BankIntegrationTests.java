package com.pjatk.s32362Bank;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BankIntegrationTests {
    @MockitoBean
    UserStorage userStorage;
    @Autowired
    private Bank bank;

    @Test
    void registerUserSuccessfully() {
        String userId = "A";
        when(userStorage.getUser(userId)).thenReturn(null);
        TransactionResult result = bank.registerUser(userId, 1000);
        assertThat(result.isAccepted()).isTrue();
    }

    @Test
    void registerUserFailureBecauseUserExists() {
        String userId = "A";
        when(userStorage.getUser(userId)).thenReturn(new User(userId, 1000));
        TransactionResult result = bank.registerUser(userId, 1000);
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void initiatePaymentSuccessfully() {
        String userId = "A";
        when(userStorage.getUser(userId)).thenReturn(new User(userId, 1000));
        TransactionResult result = bank.initiatePayment(userId, 400);
        assertThat(result.isAccepted()).isTrue();
    }

    @Test
    void initiatePaymentFailureBecauseUserDoesNotExist() {
        String userId = "A";
        when(userStorage.getUser(userId)).thenReturn(null);
        TransactionResult result = bank.initiatePayment(userId, 400);
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void initiatePaymentFailureBecauseNotEnoughFunds() {
        String userId = "A";
        when(userStorage.getUser(userId)).thenReturn(new User(userId, 1000));
        TransactionResult result = bank.initiatePayment(userId, 1001);
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void depositMoneySuccessfully() {
        String userId = "A";
        when(userStorage.getUser(userId)).thenReturn(new User(userId, 1000));
        TransactionResult result = bank.depositMoney(userId, 400);
        assertThat(result.isAccepted()).isTrue();
    }

    @Test
    void depositMoneyFailureBecauseUserDoesNotExist() {
        String userId = "A";
        when(userStorage.getUser(userId)).thenReturn(null);
        TransactionResult result = bank.depositMoney(userId, 400);
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void getUserDataSuccessfully() {
        String userId = "A";
        when(userStorage.getUser(userId)).thenReturn(new User(userId, 1000));
        TransactionResult result = bank.getUserData(userId);
        assertThat(result.isAccepted()).isTrue();
    }

    @Test
    void getUserDataFailureBecauseUserDoesNotExist() {
        String userId = "A";
        when(userStorage.getUser(userId)).thenReturn(null);
        TransactionResult result = bank.getUserData(userId);
        assertThat(result.isDeclined()).isTrue();
    }

    @Test
    void getUserDataCheckResult() {
        String userId = "A";
        when(userStorage.getUser(userId)).thenReturn(new User(userId, 1000));
        TransactionResult result = bank.getUserData(userId);
        assertThat(result.getMessage()).isEqualTo("User A: Balance = 1000");
    }
}
