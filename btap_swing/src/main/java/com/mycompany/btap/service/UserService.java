package com.mycompany.btap.service;

import com.mycompany.btap.api.ApiClient;
import com.mycompany.btap.api.ApiService;
import com.mycompany.btap.api.model.ApiResponse;
import com.mycompany.btap.api.model.LoginRequest;
import com.mycompany.btap.api.model.RegisterRequest;
import com.mycompany.btap.model.User;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

// This service class now uses Retrofit to interact with the PHP API.
// The logic is much cleaner as Retrofit handles all the HTTP and JSON details.
public class UserService {

    private final ApiService apiService;

    public UserService() {
        this.apiService = ApiClient.getApiService();
    }

    /**
     * Attempts to log in a user by making an API call.
     * @param username The user's username.
     * @param password The user's password.
     * @return The User object on success.
     * @throws Exception if login fails, credentials are bad, or a network error occurs.
     */
    public User loginUser(String username, String password) throws Exception {
        LoginRequest request = new LoginRequest(username, password);
        Call<ApiResponse<User>> call = apiService.loginUser(request);

        try {
            Response<ApiResponse<User>> response = call.execute(); // Synchronous call

            if (response.isSuccessful() && response.body() != null) {
                ApiResponse<User> apiResponse = response.body();
                if (apiResponse.isSuccess()) {
                    return apiResponse.getUser();
                } else {
                    // Throw an exception with the message from the API (e.g., "Invalid username or password")
                    throw new Exception(apiResponse.getMessage());
                }
            } else {
                // Handle cases where the server returns an error code (e.g., 404, 500)
                throw new IOException("Server error: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            // Handle network errors (e.g., server not reachable)
            throw new IOException("Network error: Could not connect to the server. " + e.getMessage(), e);
        }
    }

    /**
     * Attempts to register a new user by making an API call.
     * @param username The new user's username.
     * @param password The new user's password.
     * @param email The new user's email.
     * @return A success message from the API.
     * @throws Exception if registration fails or a network error occurs.
     */
    public String registerUser(String username, String password, String email) throws Exception {
        RegisterRequest request = new RegisterRequest(username, password, email);
        Call<ApiResponse<Void>> call = apiService.registerUser(request);

        try {
            Response<ApiResponse<Void>> response = call.execute(); // Synchronous call

            if (response.isSuccessful() && response.body() != null) {
                ApiResponse<Void> apiResponse = response.body();
                if (apiResponse.isSuccess()) {
                    return apiResponse.getMessage(); // e.g., "User registered successfully."
                } else {
                    // e.g., "Username or email already exists."
                    throw new Exception(apiResponse.getMessage());
                }
            } else {
                throw new IOException("Server error: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            throw new IOException("Network error: Could not connect to the server. " + e.getMessage(), e);
        }
    }
}
