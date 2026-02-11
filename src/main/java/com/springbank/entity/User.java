package com.springbank.entity;


import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @Column(unique = true, nullable = false)
    private String userId;

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
