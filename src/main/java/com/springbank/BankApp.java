package com.springbank;

import com.springbank.config.AppConfig;
import com.springbank.ui.Menu;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BankApp {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            Menu menu = context.getBean(Menu.class);
            while (true) {
                if (!menu.loggedIn()) {
                    menu.loginPage();
                } else {
                    menu.showMainMenu();
                }
            }
        }
    }
}
