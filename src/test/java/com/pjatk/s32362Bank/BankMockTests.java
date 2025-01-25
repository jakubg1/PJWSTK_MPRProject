package com.pjatk.s32362Bank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankMockTests {
    @Mock
    UserStorage userStorage;
    @InjectMocks
    Bank bank;

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
