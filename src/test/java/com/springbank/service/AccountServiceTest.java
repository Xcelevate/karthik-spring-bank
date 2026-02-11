package com.springbank.service;


import com.springbank.entity.Account;
import com.springbank.entity.Transaction;
import com.springbank.exception.AmountException;
import com.springbank.repository.AccountRepository;
import com.springbank.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.AccountNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(accountRepository, transactionRepository);
    }

    @Nested
    class NewAccountTest {
        @Test
        void createAccountWithValid() {
            String name = "John";
            double amt = 4342.2;
            accountService.newAccount(name, amt);

            verify(accountRepository, atLeast(1)).save(any(Account.class));
        }

        @Test
        void shouldThrowExceptionWhenLessThan100() {
            String name = "kk";
            double amt = 90;

            assertThrows(AmountException.class, () -> accountService.newAccount(name, amt));
        }
    }

    @Nested
    class GetBalanceAmount {
        @Test
        void falseWhenUserNotFound() {
            int accNo = 423;
            String name = "karthik";
            when(accountRepository.findByAccountIdAndUserId(accNo, name)).thenReturn(null);
            assertThrows(AccountNotFoundException.class, () -> accountService.getBalanceAmount(accNo, name));
        }

        @Test
        void shouldReturnBalanceWhenAccountExists() throws AccountNotFoundException {
            int accNo = 123;
            String userId = "john";
            Account account = new Account(userId, 500.0);
            when(accountRepository.findByAccountIdAndUserId(accNo, userId)).thenReturn(account);
            double balance = accountService.getBalanceAmount(accNo, userId);
            assertEquals(500.0, balance);
            verify(accountRepository, times(1)).findByAccountIdAndUserId(accNo, userId);
        }

        @Test
        void trueWhenUserNotFound() throws AccountNotFoundException {
            int accNo = 123;
            String userId = "john";

            Account acc = new Account("john" , 123);
            when(accountRepository.findByAccountIdAndUserId(accNo, userId)).thenReturn(acc);

            accountService.getBalanceAmount(accNo, userId);

            verify(accountRepository, atMostOnce()).findByAccountIdAndUserId(accNo, userId);
        }
    }

    @Nested
    class Deposit{

        @Test
        void throwAmountExceptionWhenLesThanZero(){
            int accNo = 123;
            String userId = "Karthik";
            double balance = 0;
            assertThrows(AmountException.class, () -> accountService.deposit(accNo, balance, userId));
        }

        @Test
        void throwAmountExceptionWhenAmountIsNegative(){
            int accNo = 123;
            String userId = "Karthik";
            double balance = -100;
            assertThrows(AmountException.class, () -> accountService.deposit(accNo, balance, userId));
        }

        @Test
        void throwAccountNotFoundExceptionWhenUserNotFound(){
            int accNo = 123;
            String userId = "Karthik";
            when(accountRepository.findByAccountIdAndUserId(accNo, userId)).thenReturn(null);
            assertThrows(AccountNotFoundException.class , () -> accountService.deposit(accNo , 523, userId));
        }

        @Test
        void accountRepoCalled() throws AccountNotFoundException {
            int accNo = 123;
            String userId = "Karthik";
            double amt = 433;
            Account acc = new Account(userId, 42);
            when(accountRepository.findByAccountIdAndUserId(accNo, userId)).thenReturn(acc);

            accountService.deposit(accNo , amt , userId);

            verify(accountRepository ,  atMostOnce()).save(any(Account.class));
        }
        @Test
        void checkTransactionSuccess() throws AccountNotFoundException {
            int accNo = 123;
            String userId = "Karthik";
            double amt = 433;
            Account acc = new Account(userId, 0);
            when(accountRepository.findByAccountIdAndUserId(accNo, userId)).thenReturn(acc);

            accountService.deposit(accNo , amt , userId);

            verify(accountRepository ,  atMostOnce()).save(any(Account.class));
            verify(transactionRepository, atMostOnce()).save(any(Transaction.class));

        }
    }

    @Nested
    class Withdraw{
        @Test
    }
}

