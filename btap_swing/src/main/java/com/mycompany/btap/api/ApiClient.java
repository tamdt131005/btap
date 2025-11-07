package com.mycompany.btap.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// This class provides a singleton instance of the Retrofit client.
public class ApiClient {

    // The base URL of your local PHP API
    // IMPORTANT: Make sure your XAMPP server is running and the project is in htdocs.
    private static final String BASE_URL = "http://localhost/btap_swing_api/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
                    .build();
        }
        return retrofit;
    }
    
    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }
}
