package com.springbank.service;


import com.springbank.entity.Account;
import com.springbank.entity.Transaction;
import com.springbank.exception.AmountException;
import com.springbank.repository.AccountRepository;
import com.springbank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import com.springbank.exception.AccountNotFoundException;
import java.util.List;

@Service
public class AccountService {

    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    AccountService(AccountRepository ar , TransactionRepository tr) {
        accountRepository = ar;
        transactionRepository = tr;
    }


    public List<Account> getAccounts() {
        return accountRepository.findByUserId(UserService.getCurrentUserId());
    }

    public void newAccount(String uId , double amt) throws AmountException {
        if(amt < 100) {
            throw new AmountException("Initial Amount at least 100. ");
        }
        Account account = new Account(uId , amt);
        accountRepository.save(account);
    }



    public double getBalanceAmount(int accNo, String currentUserId) throws AccountNotFoundException {
        Account acc = accountRepository.findByAccountIdAndUserId(accNo, currentUserId);
        if(acc == null ) {
            throw new AccountNotFoundException("Account not found");
        }
        return acc.getBalance();
    }

    public void deposit(int accNo, double amt , String currentUserId) throws AccountNotFoundException , AmountException {
        if (amt <= 0) {
            throw new AmountException("Amount must be greater than 0");
        }
        Account acc = accountRepository.findByAccountIdAndUserId(accNo,currentUserId);
        if(acc == null) {
            throw new AccountNotFoundException("Account not found");
        }
        acc.setBalance(acc.getBalance() + amt);
        accountRepository.save(acc);
        Transaction trans = new Transaction(currentUserId , acc.getAccountId() , amt , "CREDIT");
        transactionRepository.save(trans);

    }

    public void withdraw(int accNo, double amt, String currentUserId) throws AccountNotFoundException , AmountException {
        if (amt <= 0) {
            throw new AmountException("Amount must be greater than 0");
        }
        Account acc = accountRepository.findByAccountIdAndUserId(accNo,currentUserId);
        if(acc == null) {
            throw new AccountNotFoundException("Account Not Found");
        }
        if(acc.getBalance() < amt) {
            throw new AmountException("Insufficient Balance");
        }
        acc.setBalance(acc.getBalance() - amt);
        accountRepository.save(acc);
        Transaction trans = new Transaction(currentUserId , acc.getAccountId() , amt , "DEBIT");
        transactionRepository.save(trans);
    }
}
