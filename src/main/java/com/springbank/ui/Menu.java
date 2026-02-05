package com.springbank.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class Menu {
    Scanner kk;
    MenuController menuControl;
    boolean currentUser;

    public Menu(MenuController menuController ,Scanner scanner) {
        kk = scanner;
        menuControl = menuController;
    }


    public void loginPage() {
        System.out.println("\nWelcome to Karthik's Bank Login Menu\n");
        System.out.println("1. Login\n2. Sign up\n3. Exit");
        System.out.print("Choice: ");
        String choice = kk.nextLine();

        switch (choice) {
            case "1" -> {
                System.out.print("\nPlease enter your userId: ");
                String userId = kk.nextLine();
                System.out.print("Please enter your password: ");
                String password = kk.nextLine();
                if (menuControl.login(userId, password)) {
                    currentUser = true;
                    System.out.println("\nLogin Successful. Welcome: " + userId);
                    System.out.print("\nWelcome to Karthik's Bank \n");
                }
            }
            case "2" -> {
                System.out.print("Please enter your userId: ");
                String userId = kk.nextLine();
                System.out.print("Please enter your password: ");
                String password = kk.nextLine();
                menuControl.registerUser(userId , password);
            }
            case "3" -> System.exit(0);
            default -> System.out.println("Invalid choice. Please try again");
        }
    }

    public boolean loggedIn() {
        return currentUser;
    }

    public void showMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.print("1. View My Accounts\n" + "2. Create New Account\n" + "3. View Balance\n");
        System.out.println("4. Deposit Money\n" + "5. Withdraw Money\n" + "6. Transfer Money\n" + "7.Account Transaction Details\n" + "8. Logout");
        System.out.print("Selection: ");
        String choice = kk.nextLine();
        try {
            switch (choice) {
                case "1" -> menuControl.listAccounts();
                case "2" -> {

                    System.out.println("Initial Amount for creation: ");
                    double amount = kk.nextDouble();
                    kk.nextLine();
                    menuControl.createAccount(amount);

                }
                case "3" -> {
                    System.out.print("Enter Account ID: ");
                    int accNo = Integer.parseInt(kk.nextLine());
                    menuControl.getBalance(accNo);
                }
                case "4" -> {
                    System.out.print("Enter Account ID: ");
                    int accNo = Integer.parseInt(kk.nextLine());
                    System.out.print("Enter Amount to deposit: ");
                    double amount = kk.nextDouble();
                    kk.nextLine();
                    menuControl.deposit(accNo, amount);
                }
                case "5" -> {
                    System.out.print("Enter Account ID: ");
                    int accNo = Integer.parseInt(kk.nextLine());
                    System.out.print("Enter Amount to withdraw: ");
                    double amount = kk.nextDouble();
                    kk.nextLine();
                    menuControl.debit(accNo, amount);
                }
                case "6" -> {
                    System.out.print("    1.Self Transfer.\n    2.Another User Transfer.\n    3.Exit\nchoose: ");
                    int trans = Integer.parseInt(kk.nextLine());
                    switch (trans) {
                        case 1 -> {
                            System.out.print("Enter From Account ID: ");
                            int fromAccNo = Integer.parseInt(kk.nextLine());
                            System.out.print("Enter To Account ID: ");
                            int toAccNo = Integer.parseInt(kk.nextLine());
                            System.out.print("Enter Amount to transfer: ");
                            double amount = kk.nextDouble();
                            kk.nextLine();
                            menuControl.selfTransfer(fromAccNo, toAccNo, amount);
                        }
                        case 2 -> {
                            System.out.print("Enter From Account ID: ");
                            int fromAccNo = Integer.parseInt(kk.nextLine());
                            System.out.print("Enter To User ID: ");
                            String toUserId = kk.nextLine();
                            System.out.print("Enter To Account ID: ");
                            int toAccNo = Integer.parseInt(kk.nextLine());
                            System.out.print("Enter Amount to transfer: ");
                            double amount = Double.parseDouble(kk.nextLine());
                            menuControl.toUserTransfer(fromAccNo, toUserId, toAccNo, amount);
                        }
                        case 3 -> System.out.println("Exited.");
                        default -> System.out.println("Invalid choice. Please try again");
                    }
                }
                case "7" ->{
                    System.out.print("Enter the Account Number: ");
                    int  accNo = Integer.parseInt(kk.nextLine());
                    menuControl.printTransaction(accNo);
                }
                case "8" -> {
                    currentUser = false;
                    menuControl.logout();
                    System.out.println("logged out ");
                }
                default -> System.out.println("Invalid choice. Please try again");
            }
            if (System.console() != null) {
                System.out.print("\nPlease Enter to continue... ");
                kk.nextLine();
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Invalid input. Please try again");
            System.out.print("\nPlease Enter to continue... ");
            kk.nextLine();
        }
    }
}
