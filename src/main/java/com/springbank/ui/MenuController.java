package com.springbank.ui;

import com.springbank.entity.Account;
import com.springbank.entity.Transaction;
import com.springbank.exception.AmountException;
import com.springbank.exception.UserNotFoundException;
import com.springbank.exception.WrongPasswordException;
import com.springbank.service.AccountService;
import com.springbank.service.TransactionService;
import com.springbank.service.UserService;
import org.springframework.stereotype.Service;
import com.springbank.exception.AccountNotFoundException;
import java.util.List;


@Service
public class MenuController {
    UserService userService;
    AccountService accountService;
    TransactionService transactionService;

    MenuController(UserService userSer, AccountService accSer, TransactionService tranSer) {
        userService = userSer;
        accountService = accSer;
        transactionService = tranSer;
    }

    public boolean login(String userId, String password) {
        try {
            userService.findUser(userId, password);
            return true;
        } catch (WrongPasswordException | UserNotFoundException e) {
            System.out.println("Exception : " + e.getMessage());
        }
        return false;
    }


    public void listAccounts() {
        List<Account> list = accountService.getAccounts();
        if (!list.isEmpty()) {
            System.out.println("----------------------\nAccount_No   Balance \n----------------------");
            list.forEach(System.out::println);
        } else {
            System.out.println("No accounts found");
        }
    }

    public void createAccount(double amt) {
        try {
            accountService.newAccount(UserService.getCurrentUserId(), amt);
            System.out.println("Account created successfully");
        } catch (AmountException e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }

    public void getBalance(int accNo) {
        try {
            double bal = accountService.getBalanceAmount(accNo, UserService.getCurrentUserId());
            System.out.println("Balance : " + bal);
        } catch (AccountNotFoundException e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }

    public void deposit(int accNo, double amount) {
        try {
            accountService.deposit(accNo, amount, UserService.getCurrentUserId());
            System.out.println("Successfully deposited : " + amount);
        } catch (AccountNotFoundException | AmountException e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }


    public void debit(int accNo, double amount) {
        try {
            accountService.withdraw(accNo, amount, UserService.getCurrentUserId());
            System.out.println("Withdraw " + amount + " Rs successfully");
        } catch (AccountNotFoundException | AmountException e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }

    public void selfTransfer(int fromAccNo, int toAccNo, double amount) {
        try {
            if (fromAccNo == toAccNo) {
                throw new AmountException("From and To Accounts can not be same for 'Self Transfer'");
            } else if (amount <= 0) {
                throw new AmountException("Amount must be greater than 0");
            }

            transactionService.transferAmount(UserService.getCurrentUserId(), fromAccNo, toAccNo, amount);
            System.out.println("Successfully Transferred: " + amount + " Rs from '" + fromAccNo + "' to '" + toAccNo + "'.");

        } catch (AmountException | AccountNotFoundException e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }

    public void toUserTransfer(int fromAccNo, String toUserId, int toAccNo, double amount) {
        try {
            if (amount <= 0) {
                throw new AmountException("Amount must be greater than 0");
            }
            transactionService.transferAmount(UserService.getCurrentUserId(), fromAccNo, toUserId, toAccNo, amount);
        } catch (AmountException | AccountNotFoundException e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }

    public void logout() {
        UserService.setCurrentUser();
    }

    public void printTransaction(int accId){
        System.out.println("---------------------------------------------------------------");
        System.out.print("Trans_id:    User ID:     Account id:     Amount:        Type: \n");
       for(Transaction tran : transactionService.getTransactionList(UserService.getCurrentUserId() , accId)){
           System.out.println(tran);
       }
        System.out.println("---------------------------------------------------------------");
    }

    public void registerUser(String userId , String password) {
        if(userService.existUserId(userId, password)) {
            System.out.println("User " + userId + " already exists");
        }
        else {
            userService.createUser(userId , password);
            System.out.println("User " + userId + " created successfully");
        }
    }
}