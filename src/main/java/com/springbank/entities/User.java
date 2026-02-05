package com.springbank.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {
    @Id
    private String userId;

    @Column(unique = true, nullable = false)
    private String password;



    public User(String userId , String password) {
        this.userId = userId;
        this.password = password;
    }

    User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
