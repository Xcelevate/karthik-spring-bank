package com.springbank.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuTest {

    @Mock
    private MenuController menuController;
    private Menu menu;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }


    @AfterEach
    void tearDown() {
        System.setOut(System.out); // reset System.out
        System.setIn(System.in);   // reset System.in
    }

    @Test
    void testLoginSuccess() {
        String input = "1\nuser\npass\n\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        menu = new Menu(menuController, testScanner);

        when(menuController.login("user", "pass")).thenReturn(true);

        menu.loginPage();

        assertTrue(menu.loggedIn());
        assertTrue(outputStream.toString().contains("Login Successful"));
    }

    @Test
    void testLoginFailure() {
        String input = "1\nwrong\nbad\n\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        menu = new Menu(menuController, testScanner);

        when(menuController.login("wrong", "bad")).thenReturn(false);

        menu.loginPage();

        assertFalse(menu.loggedIn());
        assertTrue(outputStream.toString().contains("Welcome to Karthik's Bank Login Menu"));
    }

    @Test
    void testShowMainMenuViewAccounts() {
        // choice=1, then Enter to continue
        String input = "1\n\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        menu = new Menu(menuController, testScanner);

        menu.showMainMenu();

        verify(menuController, times(1)).listAccounts();
    }

    @Test
    void testShowMainMenuCreateAccount() {
        // choice=2, amount=500, then Enter to continue
        String input = "2\n500\n\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        menu = new Menu(menuController, testScanner);

        menu.showMainMenu();

        verify(menuController, times(1)).createAccount(500.0);
    }

    @Test
    void testShowMainMenuDeposit() {
        // choice=4, accNo=123, amount=200, then Enter
        String input = "4\n123\n200\n\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        menu = new Menu(menuController, testScanner);

        menu.showMainMenu();

        verify(menuController, times(1)).deposit(123, 200.0);
    }

    @Test
    void testShowMainMenuWithdraw() {
        String input = "5\n123\n100\n\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        menu = new Menu(menuController, testScanner);

        menu.showMainMenu();

        verify(menuController, times(1)).debit(123, 100.0);
    }

    @Test
    void testShowMainMenuSelfTransfer() {
        // choice=6, subchoice=1, from=111, to=222, amount=50, then Enter
        String input = "6\n1\n111\n222\n50\n\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        menu = new Menu(menuController, testScanner);

        menu.showMainMenu();

        verify(menuController, times(1)).selfTransfer(111, 222, 50.0);
    }

    @Test
    void testShowMainMenuToUserTransfer() {
        // choice=6, subchoice=2, from=111, toUserId=abc, toAcc=222, amount=75, then Enter
        String input = "6\n2\n111\nabc\n222\n75\n\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        menu = new Menu(menuController, testScanner);

        menu.showMainMenu();

        verify(menuController, times(1)).toUserTransfer(111, "abc", 222, 75.0);
    }

    @Test
    void testShowMainMenuLogout() {
        // choice=7, then Enter
        String input = "7\n\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        menu = new Menu(menuController, testScanner);

        menu.showMainMenu();

        assertFalse(menu.loggedIn());
        verify(menuController, times(1)).logout();
    }
}
