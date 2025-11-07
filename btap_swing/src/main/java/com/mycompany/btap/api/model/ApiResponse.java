package com.mycompany.btap.api.model;

import com.mycompany.btap.model.User;

// This is a generic response class that matches the JSON structure from the PHP API.
// It uses generics (<T>) to be able to hold different types of data (e.g., User)
public class ApiResponse<T> {
    private String status;
    private String message;
    private T user; // The data payload, which can be a User object on login

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getUser() {
        return user;
    }

    public boolean isSuccess() {
        return "success".equalsIgnoreCase(status);
    }
}
