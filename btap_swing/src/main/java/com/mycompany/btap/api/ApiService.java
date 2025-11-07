package com.mycompany.btap.api;

import com.mycompany.btap.api.model.ApiResponse;
import com.mycompany.btap.api.model.LoginRequest;
import com.mycompany.btap.api.model.RegisterRequest;
import com.mycompany.btap.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

// This interface defines the API endpoints for our application.
// Retrofit turns this interface into a working API client.
public interface ApiService {

    @POST("login.php")
    Call<ApiResponse<User>> loginUser(@Body LoginRequest loginRequest);

    @POST("register.php")
    Call<ApiResponse<Void>> registerUser(@Body RegisterRequest registerRequest);

}
