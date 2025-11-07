package com.mycompany.btap.model;

// This class is a Plain Old Java Object (POJO) that Gson will use to
// automatically map the 'user' object from the JSON API response.
public class User {

    private int id;
    private String username;
    private String email;

    // Constructor for Gson to use
    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
