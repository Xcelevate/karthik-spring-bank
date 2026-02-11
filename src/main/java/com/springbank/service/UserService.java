package com.springbank.service;

import com.springbank.entity.User;
import com.springbank.exception.UserNotFoundException;
import com.springbank.exception.WrongPasswordException;
import com.springbank.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static User currentUser;
    UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
        UserService.currentUser = null;
    }

    public boolean findUser(String userId, String password) throws WrongPasswordException ,UserNotFoundException {
        User user = userRepo.findByUserId(userId);
        UserService.currentUser = user;
        if (user == null) throw new UserNotFoundException("User not found");
        if (user.getPassword().equals(password)) return true;
        else {
            throw new WrongPasswordException("\"\"\"\"Wrong password\"\"\"\" ");
        }
    }

    public static String getCurrentUserId() {
        return currentUser.getUserId();
    }
    public static void setCurrentUser() {
        System.out.println("logging out......");
        currentUser = null;
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            System.out.println("sleep interrupted");
        }
    }

    public void createUser(String userId, String password) {
        User user = new User(userId , password);
        userRepo.save(user);
    }

    public boolean existUserId(String userId, String password) {
        return  userRepo.existsById(userId);
    }
}
