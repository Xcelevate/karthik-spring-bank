package com.springbank.service;

import com.springbank.entity.Transaction;
import com.springbank.exception.AmountException;
import com.springbank.repository.AccountRepository;
import com.springbank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import com.springbank.exception.AccountNotFoundException;

import java.util.List;

@Service
public class TransactionService {
    AccountRepository accountRepo;
    TransactionRepository transactionRepository;
    AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepo, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountRepo = accountRepo;
        this.accountService = accountService;
    }

    public void transferAmount(String userId, int fromAccNo, int toAccNo, double amount) throws AccountNotFoundException, AmountException {
        boolean accountExist = accountRepo.existsByAccountIdAndUserId(fromAccNo, userId) && accountRepo.existsByAccountIdAndUserId(toAccNo, userId);

        if (accountExist) {
            accountService.withdraw(fromAccNo, amount, userId);
            accountService.deposit(toAccNo, amount, userId);
            Transaction withdraw = new Transaction(UserService.getCurrentUserId(), fromAccNo, amount, "DEBIT");
            Transaction deposit = new Transaction(UserService.getCurrentUserId(), fromAccNo, amount, "CREDIT");
            transactionRepository.save(withdraw);
            transactionRepository.save(deposit);
        } else {
            throw new AccountNotFoundException("Account not found");
        }
    }


    public void transferAmount(String fromUserId , int fromAccNo, String toUserId , int toAccNo, double amount) throws AccountNotFoundException, AmountException {
        boolean accountExist = accountRepo.existsByAccountIdAndUserId(fromAccNo , fromUserId) && accountRepo.existsByAccountIdAndUserId(toAccNo, toUserId);
        if (accountExist) {
            accountService.withdraw(fromAccNo, amount, fromUserId);
            accountService.deposit(toAccNo, amount, toUserId);
            Transaction withdraw = new Transaction(fromUserId, fromAccNo, amount, "DEBIT");
            Transaction deposit = new Transaction(toUserId, toAccNo, amount, "CREDIT");
            transactionRepository.save(withdraw);
            transactionRepository.save(deposit);
        }
        else {
            throw new AccountNotFoundException("Account not found");
        }
    }

    public List<Transaction> getTransactionList(String uid, int accId) {
        return transactionRepository.findByUserIdAndAccId(uid , accId);
    }
}
